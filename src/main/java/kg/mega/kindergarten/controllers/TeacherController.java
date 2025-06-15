package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.TeacherCreateDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;
import kg.mega.kindergarten.models.dtos.TeacherCreateDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;
import kg.mega.kindergarten.services.TeacherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/teacher")
public class TeacherController implements CRUDController<TeacherDto, TeacherCreateDto, Teacher> {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    public  TeacherDto create(TeacherCreateDto teacherCreateDto, Position position) {
        return teacherService.create(teacherCreateDto, position) ;
    }

    @Override
    public TeacherDto create(TeacherCreateDto teacherCreateDto) {
        return null;
    }

    @Override
    public TeacherDto update(TeacherDto teacherDto, Delete delete) {
        return teacherService.update(teacherDto, delete);
    }

    @Override
    public TeacherDto delete(Long id) {
        return teacherService.delete(id);
    }

    @Override
    public List<TeacherDto> allList(int page, int size) {
        return teacherService.findAllList(page,size);
    }



    @Override
    public Teacher findById(Long id) {
        return teacherService.findById(id);
    }
}
