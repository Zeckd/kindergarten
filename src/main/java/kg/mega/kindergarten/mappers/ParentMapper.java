package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentSaveDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParentMapper {
    ParentMapper INSTANCE = Mappers.getMapper(ParentMapper.class);
    Parent parentSaveDtoToParent(ParentSaveDto parentSaveDto);



    @Mapping(target = "id", source = "parent.id")
    ParentDto parentToParentDto(Parent parent);


}
