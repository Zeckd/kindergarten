package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface GroupRepo extends JpaRepository<Group, Long> {
    @Query("select u  from Group u where u.delete = 0 and u.id = ?1")
    Group findByIdGroup(Long id);


    @Query("select u from  Group u where u.delete = 0")
    List<Group> findAllList(Pageable pageable);
    boolean existsByTeacher_Id(Long teacherId);
    boolean existsByAssistant_Id(Long assistantId);
}
