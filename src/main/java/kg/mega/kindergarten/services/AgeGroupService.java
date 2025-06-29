package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupSaveDto;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;

import java.util.List;

public interface AgeGroupService {
    AgeGroupDto create(AgeGroupSaveDto ageGroupCreateDto);

    List<AgeGroup> allList(int page, int size);

    AgeGroupDto delete(Long id);

    AgeGroupDto update(Long id ,AgeGroupSaveDto ageGroupSaveDto, Delete delete);

    AgeGroup findById(Long id);
}
