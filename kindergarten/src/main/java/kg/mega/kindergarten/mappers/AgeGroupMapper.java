package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupSaveDto;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AgeGroupMapper {
    AgeGroupMapper INSTANCE = Mappers.getMapper(AgeGroupMapper.class);
    AgeGroup ageGroupSaveDtoToAgeGroup(AgeGroupSaveDto ageGroupSaveDto);
    AgeGroupDto ageGroupToAgeGroupDto(AgeGroup ageGroup);

}
