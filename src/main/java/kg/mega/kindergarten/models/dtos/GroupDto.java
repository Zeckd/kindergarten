package kg.mega.kindergarten.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Teacher;

import java.util.List;

public record GroupDto (
        Long id,
         String name,
         AgeGroup ageGroup,
        Teacher teacher,
        Teacher assistant,
        @JsonIgnoreProperties({"group"})
        List<Child> children
){
}
