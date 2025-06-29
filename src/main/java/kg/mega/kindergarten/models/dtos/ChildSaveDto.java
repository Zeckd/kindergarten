package kg.mega.kindergarten.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public record ChildSaveDto(
        @NotBlank(message = "Имя не должно быть пустым")
        @Schema(description = "Имя ребенка", example = "Иван", required = true)
        String firstName,

        @NotBlank(message = "Фамилия не должна быть пустой")
        @Schema(description = "Фамилия ребенка", example = "Иванов", required = true)
        String lastName,

        @Schema(description = "Отчество ребенка", example = "Иванович", required = true)
        String patronymic,

        @NotNull(message = "Дата рождения обязательна")
        @Past(message = "Дата рождения должна быть в прошлом")
        @Schema(description = "Дата рождения ребенка", example = "2015-06-21", required = true, type = "string",format = "date")
        LocalDate dateOfBirth,
        Long group,
        List<Long> parentsId
){

}
