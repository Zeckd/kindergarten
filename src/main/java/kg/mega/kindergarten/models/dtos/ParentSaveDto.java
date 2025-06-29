package kg.mega.kindergarten.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public record ParentSaveDto(

        @NotBlank(message = "Имя не должно быть пустым")
        @Schema(description = "Имя родителя", example = "Айбек", required = true)
        String firstName,

        @NotBlank(message = "Фамилия не должна быть пустой")
        @Schema(description = "Фамилия родителя", example = "Усенов", required = true)
        String lastName,

        @Schema(description = "Отчество родителя", example = "Сатыбалдиевич")
        String patronymic,

        @NotNull(message = "Контактная информация обязательна")
        @Valid
        @Schema(description = "Контактная информация родителя", required = true)
        ContactSaveDto contactCreate
) {}

