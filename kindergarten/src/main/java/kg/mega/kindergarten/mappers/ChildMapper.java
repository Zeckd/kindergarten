package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ChildSaveDto;
import kg.mega.kindergarten.models.dtos.ChildDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChildMapper {
    ChildMapper INSTANCE = Mappers.getMapper(ChildMapper.class);
    @Mapping(source = "parents", target = "parents")

    @Mapping(source = "childSaveDto.group", target = "group.id")

    Child childSaveDtoToChild(ChildSaveDto childSaveDto, List<Parent> parents);

    ChildDto childToChildDto(Child child);




}
