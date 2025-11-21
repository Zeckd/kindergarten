package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.dtos.ChildDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ChildRepo extends JpaRepository<Child, Long> {
    @Query("select u  from Child u where u.delete = 0 and u.id = ?1")
    Child findByIdChild(Long id);


    @Query("select u from  Child u where u.delete = 0")
    List<Child> findAllList(Pageable pageable);

    @Query("select u from  Child u where u.delete = 0 and u.id = :childId")

    List<Child> findByIdToList(Long childId);
}
