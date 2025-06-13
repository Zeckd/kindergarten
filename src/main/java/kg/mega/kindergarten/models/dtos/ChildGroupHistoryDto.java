package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Group;

import java.time.LocalDateTime;

public record ChildGroupHistoryDto(
        Long id,
        Group group,
        Child child,
        LocalDateTime startDate,
        LocalDateTime endDate,
        double price
){
}
