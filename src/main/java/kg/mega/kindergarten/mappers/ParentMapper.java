package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentCreateDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ParentMapper {
    ParentMapper INSTANCE = Mappers.getMapper(ParentMapper.class);
    Parent parentCreateDtoToParent(ParentCreateDto parentCreateDto);



    @Mapping(target = "id", source = "parent.id")
    ParentDto parentToParentDto(Parent parent);

    Parent parentDtoToParent(ParentDto parentDto);

}
