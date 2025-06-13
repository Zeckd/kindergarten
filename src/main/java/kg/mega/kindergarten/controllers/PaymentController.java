package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.PaymentCreateDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/payment")
public class PaymentController implements CRUDController<PaymentDto, PaymentCreateDto, Payment> {
    @Override
    public PaymentDto create(PaymentCreateDto paymentCreateDto) {
        return null;
    }

    @Override
    public PaymentDto update(PaymentDto paymentDto, Delete delete) {
        return null;
    }

    @Override
    public PaymentDto delete(Long id) {
        return null;
    }

    @Override
    public List<PaymentDto> allList(int page, int size) {
        return List.of();
    }


    @Override
    public Payment findById(Long id) {
        return null;
    }
}
