package kg.mega.kindergarten.models.dtos;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public record GroupSaveDto(

        @NotBlank(message = "Название группы не должно быть пустым")
        @Schema(description = "Название группы", example = "Старшая группа А", required = true)
        String name,

        @NotNull(message = "Возрастная категория обязательна")
        @Schema(description = "ID возрастной категории", example = "1", required = true)
        Long ageGroupId
) {}

