package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Position;
import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.TeacherSaveDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;

import java.util.List;

public interface TeacherService {
    TeacherDto create(TeacherSaveDto teacherCreateDto, Position position);

    TeacherDto update(Long id, TeacherSaveDto teacherSaveDto,Position position, Delete delete);

    TeacherDto delete(Long id);

    List<Teacher> findAllList(int page, int size);

    Teacher findById(Long id);
    Teacher findByIdForGroup(Long id, Position position);

    Object findGroupByTeacherId(Long teacherId);

}
