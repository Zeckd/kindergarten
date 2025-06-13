package kg.mega.kindergarten.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kg.mega.kindergarten.enums.Delete;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "children")
public class Child extends Human{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateOfBirth;
    @ManyToOne
    @JoinColumn (name = "group_id", nullable = false)
    private Group group;
    @ManyToMany
    @JoinTable(name = "child_parents",
            joinColumns = @JoinColumn(name = "child_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "parent_id", nullable = false)
    )

    private List<Parent> parents;
    @JsonIgnore
    private Delete delete = Delete.ACTIVE;

    public Delete getDelete() {
        return delete;
    }

    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }
}
