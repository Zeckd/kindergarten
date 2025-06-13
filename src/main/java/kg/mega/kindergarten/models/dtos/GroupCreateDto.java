package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.models.AgeGroup;

public record GroupCreateDto (
        Long id,
        String name,
        Long ageGroup,
        Long teacher
){
}
