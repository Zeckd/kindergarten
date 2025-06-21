package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kg.mega.kindergarten.controllers.cruds.CRUDControllerWithStatus;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Payment;
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

    @Operation(summary = "Создать платеж с типом оплаты")
    @PostMapping("/create")
    public PaymentDto create(
            @Parameter(description = "Данные платежа", required = true)
            PaymentCreateDto paymentCreateDto,
            @Parameter(description = "Тип оплаты", required = true)
            PaymentType paymentType) {
        return paymentService.create(paymentCreateDto, paymentType);
    }

    @Override
    @Operation(summary = "Обновить платеж")
    public PaymentDto update(PaymentDto paymentDto, Delete delete) {
        return paymentService.update(paymentDto, delete);
    }

    @Override
    @Operation(summary = "Удалить платеж по ID")
    public PaymentDto delete(Long id) {
        return paymentService.delete(id);
    }

    @Override
    @Operation(summary = "Получить список всех платежей")
    public List<Payment> allList(int page, int size) {
        return paymentService.findAllList(page, size);
    }

    @Override
    @Operation(summary = "Найти платеж по ID")
    public Payment findById(Long id) {
        return paymentService.findById(id);
    }
}
