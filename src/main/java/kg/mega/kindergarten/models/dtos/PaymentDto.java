package kg.mega.kindergarten.models.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Child;

import java.time.LocalDateTime;

public record PaymentDto (
        Long id,
         Child child,
         double paymentSum,
         LocalDateTime paymentDate,
         String period,
         PaymentType paymentType
){
}
