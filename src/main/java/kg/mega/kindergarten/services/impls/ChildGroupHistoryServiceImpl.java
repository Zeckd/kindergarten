package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.mappers.ChildGroupHistoryMapper;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDebtDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.repositories.ChildGroupHistoryRepo;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import kg.mega.kindergarten.services.ChildService;
import kg.mega.kindergarten.services.PaymentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service

public class ChildGroupHistoryServiceImpl implements ChildGroupHistoryService {
    private final ChildGroupHistoryRepo childGroupHistoryRepo;
    private final ChildService childService;
    private final PaymentService paymentService;


    public ChildGroupHistoryServiceImpl(ChildGroupHistoryRepo childGroupHistoryRepo, ChildService childService, PaymentService paymentService) {
        this.childGroupHistoryRepo = childGroupHistoryRepo;

        this.childService = childService;
        this.paymentService = paymentService;
    }
    @Override
    public ChildGroupHistoryDto create(ChildGroupHistoryCreateDto childGroupHistoryCreateDto) {
        Child child = childService.findById(childGroupHistoryCreateDto.childId());
        Payment payment = paymentService.findByChildId(child.getId());
        ChildGroupHistory childGroupHistory = ChildGroupHistoryMapper.INSTANCE.childGroupHistoryCreateDtoToChildGroupHistory(childGroupHistoryCreateDto);
        childGroupHistory.setStartDate(payment.getPaymentDate());
        if (payment.getPaymentDate() == null) {
            throw new RuntimeException("У ребенка нет платежей");
        }
        childGroupHistory.setEndDate(payment.getPaymentDate().plusMonths(1).minusSeconds(1));
        childGroupHistory.setGroup(child.getGroup());
        childGroupHistory.setChild(child);
        childGroupHistory.setPrice(child.getGroup().getAgeGroup().getPrice());


        childGroupHistory = childGroupHistoryRepo.save(childGroupHistory);

        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
    }

    @Override
    public List<ChildGroupHistory> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return childGroupHistoryRepo.findAllList(pageable);

    }

    @Override
    public ChildGroupHistoryDto delete(Long id) {
        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findById(id).orElseThrow();
        childGroupHistoryRepo.deleteById(id);
        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory) ;

    }

    @Override
    public ChildGroupHistoryDto update(ChildGroupHistoryDto childGroupHistoryDto, Delete delete) {
        ChildGroupHistory childGroupHistory = ChildGroupHistoryMapper.INSTANCE.childGroupHistoryDtoToChildGroupHistory(childGroupHistoryDto);
        childGroupHistory.setDelete(delete);
        childGroupHistory = childGroupHistoryRepo.save(childGroupHistory);


        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
    }

    @Override
    public ChildGroupHistory findById(Long id) {
        return childGroupHistoryRepo.findByIdChildGroupHistory(id);

    }

    @Override
    public ChildGroupHistoryDebtDto findDebtByChildId(Long childId) {
        Child child = childService.findById(childId);
        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findTopByChildIdOrderByEndDateDesc(child.getId());
        Payment payment = paymentService.findByChildId(child.getId());
        LocalDate startDate = childGroupHistory.getStartDate().toLocalDate();
        double price = childGroupHistory.getPrice();
        int daysInMonth = startDate.lengthOfMonth();
        LocalDate lastDayOfMonth = startDate.withDayOfMonth(startDate.lengthOfMonth());
        long workingDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(lastDayOfMonth)) {
            DayOfWeek day = date.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            date = date.plusDays(1);
        }

        double count = (price / daysInMonth) * workingDays;

        double debt = (count - payment.getPaymentSum());
        ChildGroupHistoryDebtDto dto = new ChildGroupHistoryDebtDto();
        dto.setChildId(child.getId());
        if (debt <= 0) {

            dto.setDebtAmount(0);
        } else {
            dto.setDebtAmount(debt);
        }
        return dto;
    }
}
