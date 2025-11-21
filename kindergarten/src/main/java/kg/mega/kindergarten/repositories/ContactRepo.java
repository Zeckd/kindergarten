package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ContactRepo extends JpaRepository<Contact, Long> {
    @Query("select u from Contact u where u.delete = 0 and u.id = ?1")
    Contact findByIdContact(Long id);


    @Query("select u from  Contact u where u.delete = 0")
    List<Contact> findAllList(Pageable pageable);
}
