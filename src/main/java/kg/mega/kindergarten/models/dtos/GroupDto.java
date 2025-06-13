package kg.mega.kindergarten.models.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.Teacher;

public record GroupDto (
        Long id,
         String name,
         AgeGroup ageGroup,
        Teacher teacher
){
}
