package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.ContactCreateDto;
import kg.mega.kindergarten.models.dtos.ContactDto;
import kg.mega.kindergarten.models.dtos.TeacherCreateDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher teacherCreateDtoToTeacher(TeacherCreateDto teacherCreateDto);

    TeacherCreateDto teacherToTeacherCreateDto(Teacher teacher);


    @Mapping(target = "id", source = "teacher.id")

    TeacherDto teacherToTeacherDto(Teacher teacher);

    Teacher teacherDtoToTeacher(TeacherDto teacherDto);

    List<TeacherDto> teacherToTeacherDto(List<Teacher> teacher);
}
