package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.mappers.TeacherMapper;
import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.TeacherSaveDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;
import kg.mega.kindergarten.repositories.TeacherRepo;
import kg.mega.kindergarten.services.ContactService;
import kg.mega.kindergarten.services.TeacherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service

public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepo teacherRepo;
    private final ContactService contactService;

    public TeacherServiceImpl(TeacherRepo teacherRepo, ContactService contactService) {
        this.teacherRepo = teacherRepo;
        this.contactService = contactService;
    }

    @Override
    public TeacherDto create(TeacherSaveDto teacherCreateDto, Position position) {
        Contact contact = contactService.create(teacherCreateDto.contactCreate());
        Teacher teacher = TeacherMapper.INSTANCE.teacherSaveDtoToTeacher(teacherCreateDto);
        teacher.setContact(contact);
        teacher.setPosition(position);
        teacher = teacherRepo.save(teacher);




        return TeacherMapper.INSTANCE.teacherToTeacherDto(teacher) ;
    }

    @Override
    public TeacherDto update(Long id, TeacherSaveDto teacherSaveDto,Position position, Delete delete) {
        Teacher teacherId = teacherRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Contact contact = contactService.create(teacherSaveDto.contactCreate());
        Teacher teacher = TeacherMapper.INSTANCE.teacherSaveDtoToTeacher(teacherSaveDto);
        teacher.setId(id);
        teacher.setPosition(position);
        teacher.setContact(contact);
        teacher.setDelete(delete);
        teacher = teacherRepo.save(teacher);


        return TeacherMapper.INSTANCE.teacherToTeacherDto(teacher);
    }



    @Override
    public List<Teacher> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return teacherRepo.findAllList(pageable);

    }

    @Transactional
    @Override
    public TeacherDto delete(Long id) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow();
        teacherRepo.deleteById(id);
        return TeacherMapper.INSTANCE.teacherToTeacherDto(teacher) ;

    }




    @Override
    public Teacher findById(Long id) {
        Teacher teacher = teacherRepo.findByIdTeacher(id);
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
        }
        return teacher;

    }

    @Override
    public Teacher findByIdForGroup(Long id, Position position) {
        return teacherRepo.findByIdTeacher(id);
    }
}
