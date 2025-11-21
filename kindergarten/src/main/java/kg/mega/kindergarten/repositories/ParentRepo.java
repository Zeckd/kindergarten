package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.Parent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ParentRepo extends JpaRepository<Parent, Long> {
    @Query("select u from Parent u where u.delete = 0 and u.id = ?1")
    Parent findByIdParent(Long id);


    @Query("select u from  Parent u where u.delete = 0")
    List<Parent> findAllList(Pageable pageable);
    @Query("select u from Parent u where u.delete = 0 and u.id in :parentsIds")
    List<Parent> findAllParents(List<Long> parentsIds);
}
