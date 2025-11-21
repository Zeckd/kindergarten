package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.dtos.GroupSaveDto;
import kg.mega.kindergarten.models.dtos.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);
    @Mapping(source = "ageGroupId", target = "ageGroup.id")


    Group groupSaveDtoToGroup(GroupSaveDto groupSaveDto);
    GroupDto groupToGroupDto(Group group);

}
