package kg.mega.kindergarten.models.dtos;

import java.time.LocalDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public record TeacherSaveDto(

        @NotBlank(message = "Имя обязательно")
        @Schema(description = "Имя преподавателя", example = "Айбек", required = true)
        String firstName,

        @NotBlank(message = "Фамилия обязательна")
        @Schema(description = "Фамилия преподавателя", example = "Усенов", required = true)
        String lastName,

        @Schema(description = "Отчество преподавателя", example = "Сатыбалдиевич")
        String patronymic,

        @NotNull(message = "Дата рождения обязательна")
        @Past(message = "Дата рождения должна быть в прошлом")
        @Schema(description = "Дата рождения преподавателя", example = "1980-05-20", required = true, format = "date")
        LocalDate dateOfBirth,

        @NotNull(message = "Контактная информация обязательна")
        @Valid
        @Schema(description = "Контактная информация", required = true)
        ContactSaveDto contactCreate

) {}
