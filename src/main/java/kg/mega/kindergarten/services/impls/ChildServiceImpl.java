package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.mappers.ChildMapper;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ChildCreateDto;
import kg.mega.kindergarten.models.dtos.ChildDto;
import kg.mega.kindergarten.repositories.ChildRepo;
import kg.mega.kindergarten.repositories.GroupRepo;
import kg.mega.kindergarten.services.ChildService;
import kg.mega.kindergarten.services.GroupService;
import kg.mega.kindergarten.services.ParentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service

public class ChildServiceImpl implements ChildService {
    private final ChildRepo childRepo;
    private final ParentService parentService;
    private final GroupService groupService;

    public ChildServiceImpl(ChildRepo childRepo, ParentService parentService, GroupService groupService) {
        this.childRepo = childRepo;
        this.parentService = parentService;
        this.groupService = groupService;
    }

    @Override
    public ChildDto create(ChildCreateDto childCreateDto) {
        List<Parent> parents = parentService.findAll(childCreateDto.parentsId());
        if (parents == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Group groupId = groupService.findById(childCreateDto.group()) ;

        Child child = ChildMapper.INSTANCE.childCreateDtoToChild(childCreateDto ,parents);
        if(groupId != null) {

            groupId.addChild(child);
        }
        child.setGroup(groupId);



        child = childRepo.save(child);




        return ChildMapper.INSTANCE.childToChildDto(child);
    }

    @Override
    public List<Child> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return childRepo.findAllList(pageable);

    }
    @Transactional
    @Override
    public ChildDto delete(Long id) {
        Child child = childRepo.findById(id).orElseThrow();
        childRepo.deleteById(id);
        return ChildMapper.INSTANCE.childToChildDto(child) ;

    }

    @Override
    public ChildDto update(ChildDto childDto, Delete delete) {
        Child child = ChildMapper.INSTANCE.childDtoToChild(childDto);
        child.setDelete(delete);
        child = childRepo.save(child);


        return ChildMapper.INSTANCE.childToChildDto(child);
    }

    @Override
    public Child findById(Long id) {
        return childRepo.findByIdChild(id);

    }

    @Override
    public List<Child> findByIdToList(Long childId) {
        return childRepo.findByIdToList(childId);
    }


}
