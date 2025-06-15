package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.models.Contact;

import java.time.LocalDate;

public record TeacherCreateDto (
        String firstName,
        String lastName,
        String patronymic,
        Position position,
        LocalDate dateOfBirth,
        ContactCreateDto contactCreate

){
}
