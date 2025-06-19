package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.controllers.cruds.CRUDControllerWithStatus;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.PaymentCreateDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;
import kg.mega.kindergarten.models.dtos.PaymentCreateDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;
import kg.mega.kindergarten.services.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/payment")
public class PaymentController implements CRUDControllerWithStatus<PaymentDto, PaymentCreateDto, Payment, PaymentType> {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public PaymentDto create(PaymentCreateDto paymentCreateDto, PaymentType paymentType) {
        return paymentService.create(paymentCreateDto, paymentType) ;
    }


    @Override
    public PaymentDto update(PaymentDto paymentDto, Delete delete) {
        return paymentService.update(paymentDto, delete);
    }

    @Override
    public PaymentDto delete(Long id) {
        return paymentService.delete(id);
    }

    @Override
    public List<Payment> allList(int page, int size) {
        return paymentService.findAllList(page,size);
    }



    @Override
    public Payment findById(Long id) {
        return paymentService.findById(id);
    }
}
