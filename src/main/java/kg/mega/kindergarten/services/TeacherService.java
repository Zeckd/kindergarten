package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.TeacherCreateDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;

import java.util.List;

public interface TeacherService {
    TeacherDto create(TeacherCreateDto teacherCreateDto, Position position);

    TeacherDto update(TeacherDto teacherDto, Delete delete);

    TeacherDto delete(Long id);

    List<Teacher> findAllList(int page, int size);

    Teacher findById(Long id);
    Teacher findByIdForGroup(Long id, Position position);

}
