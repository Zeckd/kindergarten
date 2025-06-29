package kg.mega.kindergarten.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AgeGroupSaveDto(
        @NotBlank(message = "Название не должно быть пустым")
        String name,
        @Min(value = 1, message = "Возрастная группа должна быть не меньше 1")
        @Max(value = 7, message = "Возрастная группа должна быть не больше 7")
        int ageGroup,

        @Positive(message = "Цена должна быть положительным числом")

        double price
){
}
