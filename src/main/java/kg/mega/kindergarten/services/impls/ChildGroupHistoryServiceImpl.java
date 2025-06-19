package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.mappers.ChildGroupHistoryMapper;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.repositories.ChildGroupHistoryRepo;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import kg.mega.kindergarten.services.ChildService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ChildGroupHistoryServiceImpl implements ChildGroupHistoryService {
    private final ChildGroupHistoryRepo childGroupHistoryRepo;
    private final ChildService childService;


    public ChildGroupHistoryServiceImpl(ChildGroupHistoryRepo childGroupHistoryRepo, ChildService childService) {
        this.childGroupHistoryRepo = childGroupHistoryRepo;

        this.childService = childService;
    }
    @Override
    public ChildGroupHistoryDto create(ChildGroupHistoryCreateDto childGroupHistoryCreateDto) {
        Child child = childService.findById(childGroupHistoryCreateDto.child());
        ChildGroupHistory childGroupHistory = ChildGroupHistoryMapper.INSTANCE.childGroupHistoryCreateDtoToChildGroupHistory(childGroupHistoryCreateDto);
        childGroupHistory.setGroup(child.getGroup());
        childGroupHistory.setChild(child);
        childGroupHistory.setPrice(child.getGroup().getAgeGroup().getPrice());
        childGroupHistory = childGroupHistoryRepo.save(childGroupHistory);

        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
    }

    @Override
    public List<ChildGroupHistory> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return childGroupHistoryRepo.findAllList(pageable);

    }

    @Override
    public ChildGroupHistoryDto delete(Long id) {
        ChildGroupHistory childGroupHistory = childGroupHistoryRepo.findById(id).orElseThrow();
        childGroupHistoryRepo.deleteById(id);
        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory) ;

    }

    @Override
    public ChildGroupHistoryDto update(ChildGroupHistoryDto childGroupHistoryDto, Delete delete) {
        ChildGroupHistory childGroupHistory = ChildGroupHistoryMapper.INSTANCE.childGroupHistoryDtoToChildGroupHistory(childGroupHistoryDto);
        childGroupHistory.setDelete(delete);
        childGroupHistory = childGroupHistoryRepo.save(childGroupHistory);


        return ChildGroupHistoryMapper.INSTANCE.childGroupHistoryToChildGroupHistoryDto(childGroupHistory);
    }

    @Override
    public ChildGroupHistory findById(Long id) {
        return childGroupHistoryRepo.findByIdChildGroupHistory(id);

    }
}
