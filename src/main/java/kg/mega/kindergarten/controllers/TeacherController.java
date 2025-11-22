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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController implements CRUDControllerWithStatus<TeacherDto, TeacherSaveDto, Teacher, Position> {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    @PostMapping("/create")
    @Operation(summary = "Создать нового учителя с должностью")
    public TeacherDto create(
            @RequestBody TeacherSaveDto teacherCreateDto,
            @Parameter(description = "Должность учителя", required = true) @RequestParam Position position) {
        return teacherService.create(teacherCreateDto, position);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "Обновить данные учителя")
    public TeacherDto update(@RequestParam Long id, @RequestBody TeacherSaveDto teacherSaveDto, @RequestParam Position position, @RequestParam Delete delete) {
        return teacherService.update(id,teacherSaveDto,position, delete);


    }



    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить учителя по ID")
    public TeacherDto delete(@RequestParam Long id) {
        return teacherService.delete(id);
    }

    @Override
    @GetMapping("/get-list")
    @Operation(summary = "Получить список всех учителей")
    public List<Teacher> allList(@RequestParam int page, @RequestParam int size) {
        return teacherService.findAllList(page, size);
    }

    @Override
    @GetMapping("/find-by-id")
    @Operation(summary = "Найти учителя по ID")
    public Teacher findById(@RequestParam Long id) {
        return teacherService.findById(id);
    }

    @Operation(summary = "Получить группу, в которой работает учитель")
    @GetMapping("/group")
    public Object getTeacherGroup(@RequestParam Long teacherId) {
        return teacherService.findGroupByTeacherId(teacherId);
    }
}
