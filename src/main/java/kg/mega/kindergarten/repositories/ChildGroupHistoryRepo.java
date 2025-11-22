package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.ChildGroupHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ChildGroupHistoryRepo extends JpaRepository<ChildGroupHistory, Long> {
    @Query("select u from ChildGroupHistory u where u.delete = 0 and u.id = ?1")
    ChildGroupHistory findByIdChildGroupHistory(Long id);


    @Query("select u from  ChildGroupHistory u where u.delete = 0")
    List<ChildGroupHistory> findAllList(Pageable pageable);

    @Query("select u from ChildGroupHistory u where u.delete = 0 and u.child.id = ?1 order by u.endDate desc")
    ChildGroupHistory findTopByChildIdOrderByEndDateDesc(Long childId);

    @Query("select u from ChildGroupHistory u where u.delete = 0 and u.child.id = ?1")
    List<ChildGroupHistory> findAllByChildId(Long childId);
}
