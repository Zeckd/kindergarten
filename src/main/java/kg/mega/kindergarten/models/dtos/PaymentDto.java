package kg.mega.kindergarten.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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

        @Schema(description = "Дата и время начала периода", example = "2025-06-21 16:19:04")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
         LocalDateTime paymentDate,
         String period,
         PaymentType paymentType
){
}
