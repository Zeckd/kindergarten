package kg.mega.kindergarten.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Group;

import java.time.LocalDateTime;

public record ChildGroupHistoryDto(
        Long id,

        @NotNull(message = "Группа обязательна")
        @JsonIgnoreProperties({"children"})
        @Schema(description = "Группа ребенка", required = true)
        Group group,

        @NotNull(message = "Ребенок обязателен")
        @Schema(description = "Ребенок", required = true)
        Child child,

        @NotNull(message = "Дата начала обязательна")
        @Schema(description = "Дата и время начала периода", example = "2025-06-21T16:19:04")
        LocalDateTime startDate,

        @NotNull(message = "Дата окончания обязательна")
        @Schema(description = "Дата и время окончания периода", example = "2025-07-21T16:19:03")
        LocalDateTime endDate,

        @Positive(message = "Цена должна быть положительной")
        @Schema(description = "Цена за период", example = "10000")
        double price
){
}
