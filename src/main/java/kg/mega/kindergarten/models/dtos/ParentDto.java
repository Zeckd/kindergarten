package kg.mega.kindergarten.models.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Contact;

public record ParentDto(
        Long id,
        String firstName,
        String lastName,
        String patronymic,
        Role role,
        Contact contact
) {
}
