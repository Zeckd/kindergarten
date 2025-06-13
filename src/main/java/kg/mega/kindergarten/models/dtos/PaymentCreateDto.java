package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Child;

import java.time.LocalDateTime;

public record PaymentCreateDto(
        Long child,
        double paymentSum,
        LocalDateTime paymentDate,
        String period,
        PaymentType paymentType
) {
}
