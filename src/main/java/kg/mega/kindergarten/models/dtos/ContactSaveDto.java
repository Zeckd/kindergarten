package kg.mega.kindergarten.models.dtos;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public record ContactSaveDto(

        @NotBlank(message = "Основной номер телефона обязателен")
        @Pattern(
                regexp = "\\+996\\d{9}",
                message = "Основной номер должен начинаться с +996 и содержать всего 13 символов (например, +996700123456)"
        )
        @Schema(description = "Основной номер телефона (только в формате +996)", example = "+996700123456", required = true)
        String phoneNumber,

        @Pattern(
                regexp = "\\+996\\d{9}",
                message = "Дополнительный номер должен начинаться с +996 и содержать всего 13 символов (например, +996555123456)"
        )
        @Schema(description = "Дополнительный номер телефона (только в формате +996)", example = "+996555123456")
        String secondaryPhoneNumber,

        @NotBlank(message = "Электронная почта обязательна")
        @Email(message = "Некорректный формат электронной почты")
        @Schema(description = "Электронная почта", example = "user@example.com", required = true)
        String email
) {}

