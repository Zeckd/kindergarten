package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.models.AgeGroup;

public record GroupCreateDto (
        String name,
        Long ageGroup
){
}
