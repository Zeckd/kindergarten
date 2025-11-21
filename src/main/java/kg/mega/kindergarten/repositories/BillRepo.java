package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
    @Query("select b from Bill b where b.delete = 0 and b.id = ?1")
    Optional<Bill> findByIdActive(Long id);

    @Query("select b from Bill b where b.delete = 0 and b.child.id = ?1 and b.status = 'PENDING' order by b.createdAt desc")
    Optional<Bill> findLatestPendingByChildId(Long childId);
}

