package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.mappers.PaymentMapper;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.PaymentSaveDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;
import kg.mega.kindergarten.repositories.PaymentRepo;
import kg.mega.kindergarten.services.ChildService;
import kg.mega.kindergarten.services.PaymentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    private final ChildService childService;

    public PaymentServiceImpl(PaymentRepo paymentRepo, ChildService childService) {
        this.paymentRepo = paymentRepo;

        this.childService = childService;
    }

    @Override
    public PaymentDto create(PaymentSaveDto paymentCreateDto, PaymentType paymentType) {
        Child child = childService.findById(paymentCreateDto.childId());
        if (child == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Payment payment = PaymentMapper.INSTANCE.paymentSaveDtoToPayment(paymentCreateDto);
        payment.setChild(child);
        payment.setPaymentType(paymentType);
        payment.setPaymentDate(LocalDateTime.now());
        payment = paymentRepo.save(payment);

        return PaymentMapper.INSTANCE.paymentToPaymentDto(payment);
    }

    @Override
    public PaymentDto update(Long id, PaymentSaveDto paymentSaveDto,PaymentType paymentType, Delete delete) {
        Payment payment = paymentRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        
        // Preserve child
        Child child = payment.getChild();
        if (paymentSaveDto.childId() != null) {
             Child newChild = childService.findById(paymentSaveDto.childId());
             if (newChild != null) {
                 child = newChild;
             }
        }
        
        payment.setChild(child);
        payment.setPaymentSum(paymentSaveDto.paymentSum());
        payment.setPeriod(paymentSaveDto.period());
        // paymentDate is usually not updated, but if we want to update it to now:
        // payment.setPaymentDate(LocalDateTime.now()); 
        // Or keep existing:
        // payment.setPaymentDate(payment.getPaymentDate());
        // The original code set it to now(), let's keep it for now or maybe it was a bug?
        // Usually payment date is when payment happened. Updating record shouldn't change date unless specified.
        // But DTO doesn't have date. Let's keep existing date.
        
        payment.setPaymentType(paymentType);
        payment.setDelete(delete);
        
        payment = paymentRepo.save(payment);


        return PaymentMapper.INSTANCE.paymentToPaymentDto(payment);
    }

    @Override
    public List<Payment> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return paymentRepo.findAllList(pageable);

    }

    @Transactional
    @Override
    public PaymentDto delete(Long id) {
        Payment payment = paymentRepo.findById(id).orElseThrow();
        paymentRepo.deleteById(id);
        return PaymentMapper.INSTANCE.paymentToPaymentDto(payment) ;

    }



    @Override
    public Payment findById(Long id) {
        Payment payment = paymentRepo.findByIdPayment(id);
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
        }
        return payment;

    }

    @Override
    public Payment findByChildId(Long child) {
        return paymentRepo.findTopByChildIdOrderByPaymentDateDesc(child);
    }
    @Override
    public double sumPaymentsByChildIdAndMonth(Long child, int month, int year) {
        return paymentRepo.sumPaymentsByChildIdAndMonth(child, month, year);
    }

    @Override
    public List<Payment> findAllByChildId(Long childId) {
        return paymentRepo.findAllByChildId(childId);
    }
}
