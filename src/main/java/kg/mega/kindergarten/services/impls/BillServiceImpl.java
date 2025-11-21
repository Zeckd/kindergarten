package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.BillStatus;
import kg.mega.kindergarten.models.Bill;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.dtos.BillResponseDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDebtDto;
import kg.mega.kindergarten.repositories.BillRepo;
import kg.mega.kindergarten.services.BillService;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import kg.mega.kindergarten.services.ChildService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepo billRepo;
    private final ChildService childService;
    private final ChildGroupHistoryService childGroupHistoryService;

    public BillServiceImpl(BillRepo billRepo, ChildService childService, ChildGroupHistoryService childGroupHistoryService) {
        this.billRepo = billRepo;
        this.childService = childService;
        this.childGroupHistoryService = childGroupHistoryService;
    }

    @Override
    @Transactional
    public BillResponseDto generateBill(Long childId) {
        Child child = childService.findById(childId);
        if (child == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ребенок не найден");
        }

        ChildGroupHistoryDebtDto debtDto = childGroupHistoryService.findDebtByChildId(childId);
        double amount = debtDto.getDebtAmount();

        if (amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У ребенка нет задолженности");
        }

        billRepo.findLatestPendingByChildId(childId).ifPresent(bill -> {

        });

        Bill bill = new Bill();
        bill.setChild(child);
        bill.setAmount(amount);

        // Генерируем уникальную ссылку на оплату
        String billToken = UUID.randomUUID().toString();
        String paymentLink = String.format("https://pay.example.com/bill/%s?amount=%.2f&childId=%d",
                billToken, amount, childId);
        bill.setPaymentLink(paymentLink);

        // Генерируем QR код - используем онлайн сервис для генерации QR изображения
        // Фронтенд может использовать эту ссылку напрямую в <img src="..."> или для отображения QR
        String qrCodeUrl = String.format("https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=%s",
                paymentLink.replace(" ", "%20"));
        bill.setQrCode(qrCodeUrl);

        bill.setStatus(BillStatus.PENDING);
        bill.setCreatedAt(LocalDateTime.now());
        bill.setExpiresAt(LocalDateTime.now().plusDays(7)); // Счет действителен 7 дней

        bill = billRepo.save(bill);

        return new BillResponseDto(
                bill.getId(),
                child.getId(),
                bill.getAmount(),
                bill.getPaymentLink(),
                bill.getQrCode(),
                bill.getStatus()
        );
    }

    @Override
    public BillResponseDto getBillStatus(Long billId) {
        Bill bill = billRepo.findByIdActive(billId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Счет не найден"));

        if (bill.getStatus() == BillStatus.PENDING && bill.getExpiresAt().isBefore(LocalDateTime.now())) {
            bill.setStatus(BillStatus.EXPIRED);
            bill = billRepo.save(bill);
        }


        if (bill.getStatus() == BillStatus.PENDING) {

        }

        return new BillResponseDto(
                bill.getId(),
                bill.getChild().getId(),
                bill.getAmount(),
                bill.getPaymentLink(),
                bill.getQrCode(),
                bill.getStatus()
        );
    }
}

