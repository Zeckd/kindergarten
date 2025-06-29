package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Teacher;
import kg.mega.kindergarten.models.dtos.TeacherSaveDto;
import kg.mega.kindergarten.models.dtos.TeacherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    Teacher teacherSaveDtoToTeacher(TeacherSaveDto teacherSaveDto);



    @Mapping(target = "id", source = "teacher.id")

    TeacherDto teacherToTeacherDto(Teacher teacher);


}
