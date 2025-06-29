package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kg.mega.kindergarten.controllers.cruds.CRUDControllerWithStatus;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.TeacherSaveDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;
import kg.mega.kindergarten.services.TeacherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController implements CRUDControllerWithStatus<TeacherDto, TeacherSaveDto, Teacher, Position> {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    @Operation(summary = "Создать нового учителя с должностью")
    public TeacherDto create(
            TeacherSaveDto teacherCreateDto,
            @Parameter(description = "Должность учителя", required = true) Position position) {
        return teacherService.create(teacherCreateDto, position);
    }

    @Override
    @Operation(summary = "Обновить данные учителя")

    public TeacherDto update(Long id, TeacherSaveDto teacherSaveDto, Position position, Delete delete) {
        return teacherService.update(id,teacherSaveDto,position, delete);


    }



    @Override
    @Operation(summary = "Удалить учителя по ID")
    public TeacherDto delete(Long id) {
        return teacherService.delete(id);
    }

    @Override
    @Operation(summary = "Получить список всех учителей")
    public List<Teacher> allList(int page, int size) {
        return teacherService.findAllList(page, size);
    }

    @Override
    @Operation(summary = "Найти учителя по ID")
    public Teacher findById(Long id) {
        return teacherService.findById(id);
    }
}
