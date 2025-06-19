package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.dtos.GroupCreateDto;
import kg.mega.kindergarten.models.dtos.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);
    @Mapping(source = "ageGroup", target = "ageGroup.id")


    Group groupCreateDtoToGroup(GroupCreateDto groupCreateDto);
    GroupDto groupToGroupDto(Group group);
    Group groupDtoToGroup(GroupDto groupDto);

    List<GroupDto> groupToGroupDto(List<Group> group);
}
