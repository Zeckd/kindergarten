package kg.mega.kindergarten.models.dtos;

import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Contact;

public record ParentCreateDto(
        Role role,
        Long contact
) {
}
