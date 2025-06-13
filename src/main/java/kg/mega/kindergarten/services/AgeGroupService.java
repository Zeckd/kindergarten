package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupCreateDto;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;

import java.util.List;

public interface AgeGroupService {
    AgeGroupDto create(AgeGroupCreateDto ageGroupCreateDto);

    List<AgeGroupDto> allList(int page, int size);

    AgeGroupDto delete(Long id);

    AgeGroupDto update(AgeGroupDto ageGroupDto, Delete delete);

    AgeGroup findById(Long id);
}
