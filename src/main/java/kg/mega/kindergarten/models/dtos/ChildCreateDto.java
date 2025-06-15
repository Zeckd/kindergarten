package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.Parent;

import java.time.LocalDate;
import java.util.List;

public record ChildCreateDto (
        String firstName,
        String lastName,
        String patronymic,
        LocalDate dateOfBirth,
        Long group,
        List<Long> parents
){

}
