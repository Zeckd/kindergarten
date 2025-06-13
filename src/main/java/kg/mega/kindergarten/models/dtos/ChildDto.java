package kg.mega.kindergarten.models.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.Parent;

import java.time.LocalDate;
import java.util.List;

public record ChildDto(
         Long id,
         LocalDate dateOfBirth,
        Group group,
        List<Parent> parents
) {
}
