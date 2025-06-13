package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupCreateDto;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AgeGroupMapper {
    AgeGroupMapper INSTANCE = Mappers.getMapper(AgeGroupMapper.class);
    AgeGroup ageGroupCreateDtoToAgeGroup(AgeGroupCreateDto ageGroupCreateDto);
    AgeGroupDto ageGroupToAgeGroupDto(AgeGroup ageGroup);
    AgeGroup ageGroupDtoToAgeGroup(AgeGroupDto ageGroupDto);
    List<AgeGroupDto> ageGroupToAgeGroupDto(List<AgeGroup> ageGroup);

}
