package kg.mega.kindergarten.models.dtos;

import jakarta.validation.constraints.NotNull;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Group;

import java.time.LocalDateTime;

public record ChildGroupHistoryCreateDto (
        @NotNull(message = "ID группы обязателен")
        Long groupId,

        @NotNull(message = "ID ребенка обязателен")
        Long childId
){
}
