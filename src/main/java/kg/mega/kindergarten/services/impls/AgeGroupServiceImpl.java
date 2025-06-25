package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.mappers.AgeGroupMapper;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupCreateDto;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;
import kg.mega.kindergarten.repositories.AgeGroupRepo;
import kg.mega.kindergarten.services.AgeGroupService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgeGroupServiceImpl implements AgeGroupService {
    private final AgeGroupRepo ageGroupRepo;

    public AgeGroupServiceImpl(AgeGroupRepo ageGroupRepo) {
        this.ageGroupRepo = ageGroupRepo;

    }

    @Override
    public AgeGroupDto create(AgeGroupCreateDto ageGroupCreateDto) {
        AgeGroup ageGroup = AgeGroupMapper.INSTANCE.ageGroupCreateDtoToAgeGroup(ageGroupCreateDto);
        ageGroup = ageGroupRepo.save(ageGroup);
        return AgeGroupMapper.INSTANCE.ageGroupToAgeGroupDto(ageGroup);
    }

    @Override
    public List<AgeGroup> allList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return ageGroupRepo.findAllList(pageable);

    }

    @Transactional
    @Override
    public AgeGroupDto delete(Long id) {
        AgeGroup ageGroup = ageGroupRepo.findById(id).orElseThrow();
        ageGroupRepo.deleteById(id);
        return AgeGroupMapper.INSTANCE.ageGroupToAgeGroupDto(ageGroup) ;

    }

    @Override
    public AgeGroupDto update(AgeGroupDto ageGroupDto, Delete delete) {
        AgeGroup ageGroup = AgeGroupMapper.INSTANCE.ageGroupDtoToAgeGroup(ageGroupDto);
        ageGroup.setDelete(delete);
        ageGroup = ageGroupRepo.save(ageGroup);


        return AgeGroupMapper.INSTANCE.ageGroupToAgeGroupDto(ageGroup);
    }

    @Override
    public AgeGroup findById(Long id) {
        return ageGroupRepo.findByIdAgeGroup(id);

    }
}
