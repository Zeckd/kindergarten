package kg.mega.kindergarten.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Child;

import java.time.LocalDateTime;

public record PaymentDto (
        Long id,
         @JsonIgnore
         Child child,
         @JsonIgnore
         double paymentSum,
         LocalDateTime paymentDate,
         @JsonIgnore
         String period,
         @JsonIgnore
         PaymentType paymentType
){
}
