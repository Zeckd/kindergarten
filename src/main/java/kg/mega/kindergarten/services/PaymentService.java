package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.PaymentSaveDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto create(PaymentSaveDto paymentCreateDto, PaymentType paymentType);
    PaymentDto update(Long id, PaymentSaveDto paymentSaveDto, PaymentType paymentType, Delete delete);


    PaymentDto delete(Long id);

    List<Payment> findAllList(int page, int size);

    Payment findById(Long id);
    

    double sumPaymentsByChildIdAndMonth(Long child, int month, int year);

    Payment findByChildId(Long id);

}
