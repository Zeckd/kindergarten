package kg.mega.kindergarten.models.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.models.Contact;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record TeacherDto(

        Long id,

        @NotBlank(message = "Имя обязательно")
        @Schema(description = "Имя преподавателя", example = "Айбек", required = true)
        String firstName,

        @NotBlank(message = "Фамилия обязательна")
        @Schema(description = "Фамилия преподавателя", example = "Усенов", required = true)
        String lastName,

        @Schema(description = "Отчество преподавателя", example = "Сатыбалдиевич")
        String patronymic,

        @NotNull(message = "Должность обязательна")
        @Schema(description = "Должность преподавателя", example = "TEACHER", required = true)
        Position position,

        @NotNull(message = "Дата рождения обязательна")
        @Past(message = "Дата рождения должна быть в прошлом")
        @Schema(description = "Дата рождения", example = "1980-05-20", format = "date", required = true)
        LocalDate dateOfBirth,

        @NotNull(message = "Контактная информация обязательна")
        @Valid
        @Schema(description = "Контактная информация", required = true)
        Contact contact

) {}

