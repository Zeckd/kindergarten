package kg.mega.kindergarten.repositories;

import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.TeacherDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    @Query("select u  from Teacher u where u.delete = 0 and u.id = ?1")
    Teacher findByIdTeacher(Long id);


    @Query("select new kg.mega.kindergarten.models.dtos.TeacherDto(u.id, u.position, u.dateOfBirth, u.contact) from  Teacher u where u.delete = 0")
    List<TeacherDto> findAllList(Pageable pageable);
}
