package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    @Query("select u from Payment u where u.delete = 0 and u.id = ?1")
    Payment findByIdPayment(Long id);


    @Query("select u from  Payment u where u.delete = 0")
    List<Payment> findAllList(Pageable pageable);
    Payment findTopByChildIdOrderByPaymentDateDesc(Long childId);
    @Query("SELECT coalesce(sum(p.paymentSum), 0) FROM Payment p WHERE p.child.id = ?1 AND month (p.paymentDate) = ?2 AND year(p.paymentDate) = ?3")
    Double sumPaymentsByChildIdAndMonth(Long childId, int month, int year);

    @Query("select p from Payment p where p.delete = 0 and p.child.id = :childId")
    List<Payment> findAllByChildId(Long childId);
}
