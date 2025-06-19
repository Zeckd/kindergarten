package kg.mega.kindergarten.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import kg.mega.kindergarten.enums.PaymentType;
import kg.mega.kindergarten.models.Child;

import java.time.LocalDateTime;

public record PaymentCreateDto(
        Long child,
        @JsonIgnore
        LocalDateTime paymentDate,
        @Schema(type = "string", pattern = "MM.yyyy", example = "10.2024")
        String period
) {
}
