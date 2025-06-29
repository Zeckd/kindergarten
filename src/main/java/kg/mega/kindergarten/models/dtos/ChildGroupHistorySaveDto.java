package kg.mega.kindergarten.models.dtos;

import jakarta.validation.constraints.NotNull;

public record ChildGroupHistorySaveDto(
        @NotNull(message = "ID группы обязателен")
        Long groupId,

        @NotNull(message = "ID ребенка обязателен")
        Long childId
){
}
