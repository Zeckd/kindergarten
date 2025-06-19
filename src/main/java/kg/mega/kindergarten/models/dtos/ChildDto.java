package kg.mega.kindergarten.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
         String firstName,
         String lastName,
         String patronymic,
         LocalDate dateOfBirth,
         @JsonIgnoreProperties({"children"})
         Group group,
         List<Parent> parents
) {
}
