package kg.mega.kindergarten.models.dtos;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public record ContactDto(

        Long id,

        @NotBlank(message = "Основной номер телефона обязателен")
        @Pattern(
                regexp = "\\+996\\d{9}",
                message = "Основной номер должен быть в формате +996XXXXXXXXX (9 цифр после +996)"
        )
        @Schema(description = "Основной номер телефона", example = "+996700123456", required = true)
        String phoneNumber,

        @Pattern(
                regexp = "\\+996\\d{9}",
                message = "Дополнительный номер должен быть в формате +996XXXXXXXXX (9 цифр после +996)"
        )
        @Schema(description = "Дополнительный номер телефона", example = "+996550123456")
        String secondaryPhoneNumber,

        @NotBlank(message = "Электронная почта обязательна")
        @Email(message = "Некорректный формат электронной почты")
        @Schema(description = "Электронная почта", example = "user@example.com", required = true)
        String email
) {}

