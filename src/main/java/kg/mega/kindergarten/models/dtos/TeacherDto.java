package kg.mega.kindergarten.models.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.models.Contact;

import java.time.LocalDate;

public record TeacherDto(
        Long id,
        Position position,
        LocalDate dateOfBirth,
        Contact contact
) {
}
