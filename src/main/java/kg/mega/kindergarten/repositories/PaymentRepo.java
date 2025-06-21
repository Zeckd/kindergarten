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
}
