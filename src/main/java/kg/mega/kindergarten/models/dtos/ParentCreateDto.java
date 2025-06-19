package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Contact;

public record ParentCreateDto(
        String firstName,
        String lastName,
        String patronymic,
        ContactCreateDto contactCreate
) {
}
