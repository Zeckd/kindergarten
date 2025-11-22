package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kg.mega.kindergarten.controllers.cruds.CRUDControllerWithStatus;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Payment;
import kg.mega.kindergarten.models.dtos.PaymentSaveDto;
import kg.mega.kindergarten.models.dtos.PaymentDto;
import kg.mega.kindergarten.services.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController implements CRUDControllerWithStatus<PaymentDto, PaymentSaveDto, Payment, PaymentType> {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    @PostMapping("/create")
    @Operation(summary = "Создать платеж с типом оплаты")
    public PaymentDto create(
            @RequestBody PaymentSaveDto paymentCreateDto,
            @Parameter(description = "Тип оплаты", required = true)
            @RequestParam PaymentType paymentType) {
        return paymentService.create(paymentCreateDto, paymentType);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "Обновить платеж")
    public PaymentDto update(@RequestParam Long id, @RequestBody PaymentSaveDto paymentSaveDto, @RequestParam PaymentType paymentType, @RequestParam Delete delete) {
        return paymentService.update(id ,paymentSaveDto, paymentType,delete);

    }


    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить платеж по ID")
    public PaymentDto delete(@RequestParam Long id) {
        return paymentService.delete(id);
    }

    @Override
    @GetMapping("/get-list")
    @Operation(summary = "Получить список всех платежей")
    public List<Payment> allList(@RequestParam int page, @RequestParam int size) {
        return paymentService.findAllList(page, size);
    }

    @Override
    @GetMapping("/find-by-id")
    @Operation(summary = "Найти платеж по ID")
    public Payment findById(@RequestParam Long id) {
        return paymentService.findById(id);
    }

    @Operation(summary = "Получить список платежей по ID ребенка")
    @GetMapping("/by-child")
    public List<Payment> getPaymentsByChild(@RequestParam Long childId) {
        return paymentService.findAllByChildId(childId);
    }

    @Operation(summary = "Получить сумму платежей ребенка за месяц")
    @GetMapping("/sum-by-month")
    public Double getSumByMonth(
            @RequestParam Long childId,
            @RequestParam int month,
            @RequestParam int year) {
        return paymentService.sumPaymentsByChildIdAndMonth(childId, month, year);
    }

    @Operation(summary = "Получить последний платеж ребенка")
    @GetMapping("/last-payment")
    public Payment getLastPayment(@RequestParam Long childId) {
        return paymentService.findByChildId(childId);
    }
}
