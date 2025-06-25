package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.mappers.ParentMapper;
import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentCreateDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import kg.mega.kindergarten.repositories.ParentRepo;
import kg.mega.kindergarten.services.ContactService;
import kg.mega.kindergarten.services.ParentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ParentServiceImpl implements ParentService {
    private final ParentRepo parentRepo;
    private final ContactService contactService;

    public ParentServiceImpl(ParentRepo parentRepo, ContactService contactService) {
        this.parentRepo = parentRepo;
        this.contactService = contactService;
    }

    @Override
    public ParentDto create(ParentCreateDto parentCreateDto, Role role) {
        Contact contact = contactService.create(parentCreateDto.contactCreate());
        Parent parent = ParentMapper.INSTANCE.parentCreateDtoToParent(parentCreateDto);
        parent.setRole(role);
        parent.setContact(contact);
        parent = parentRepo.save(parent);





        return ParentMapper.INSTANCE.parentToParentDto(parent) ;
    }

    @Override
    public ParentDto update(ParentDto parentDto, Delete delete) {
        Parent parent = ParentMapper.INSTANCE.parentDtoToParent(parentDto);
        parent.setDelete(delete);
        parent = parentRepo.save(parent);


        return ParentMapper.INSTANCE.parentToParentDto(parent);
    }

    @Override
    public List<Parent> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return parentRepo.findAllList(pageable);

    }
    @Transactional

    @Override
    public ParentDto delete(Long id) {
        Parent parent = parentRepo.findById(id).orElseThrow();
        parentRepo.deleteById(id);
        return ParentMapper.INSTANCE.parentToParentDto(parent) ;

    }




    @Override
    public Parent findById(Long id) {
        return parentRepo.findByIdParent(id);

    }

    @Override
    public List<Parent> findAll(List<Long> parents) {
        return parentRepo.findAllParents(parents);
    }
}
