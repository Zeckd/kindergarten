package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.enums.Position;

import java.time.LocalDate;

public record TeacherCreateDto (
        Position position,
        LocalDate dateOfBirth,
        Long contactId

){
}
