package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AgeGroupRepo extends JpaRepository<AgeGroup, Long> {
    @Query("select u  from AgeGroup u where u.delete = 0 and u.id = ?1")
    AgeGroup findByIdAgeGroup(Long id);


    @Query("select new kg.mega.kindergarten.models.dtos.AgeGroupDto(u.id, u.name, u.price) from  AgeGroup u where u.delete = 0")
    List<AgeGroupDto> findAllList(Pageable pageable);
}
