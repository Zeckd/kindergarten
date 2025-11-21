package kg.mega.kindergarten.models.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Contact;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public record ParentDto(
        Long id,
        @NotBlank(message = "Имя не должно быть пустым")
        @Schema(description = "Имя родителя", example = "Айбек", required = true)
        String firstName,

        @NotBlank(message = "Фамилия не должна быть пустой")
        @Schema(description = "Фамилия родителя", example = "Усенов", required = true)
        String lastName,

        @Schema(description = "Отчество родителя", example = "Сатыбалдиевич")
        String patronymic,

        @NotNull(message = "Роль обязательна")
        @Schema(description = "Роль родителя", example = "FATHER", required = true)
        Role role,

        @NotNull(message = "Контактная информация обязательна")
        @Valid
        @Schema(description = "Контактная информация родителя", required = true)
        Contact contact
) {
}
