package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.mappers.ParentMapper;
import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentSaveDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import kg.mega.kindergarten.repositories.ParentRepo;
import kg.mega.kindergarten.services.ContactService;
import kg.mega.kindergarten.services.ParentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service

public class ParentServiceImpl implements ParentService {
    private final ParentRepo parentRepo;
    private final ContactService contactService;
    private final kg.mega.kindergarten.repositories.ChildRepo childRepo;

    public ParentServiceImpl(ParentRepo parentRepo, ContactService contactService, kg.mega.kindergarten.repositories.ChildRepo childRepo) {
        this.parentRepo = parentRepo;
        this.contactService = contactService;
        this.childRepo = childRepo;
    }

    @Override
    public ParentDto create(ParentSaveDto parentCreateDto, Role role) {
        Contact contact = contactService.create(parentCreateDto.contactCreate());
        Parent parent = ParentMapper.INSTANCE.parentSaveDtoToParent(parentCreateDto);
        parent.setRole(role);
        parent.setContact(contact);
        parent = parentRepo.save(parent);





        return ParentMapper.INSTANCE.parentToParentDto(parent) ;
    }

    @Override
    public ParentDto update(Long id ,ParentSaveDto parentSaveDto,Role role, Delete delete) {
        Parent parent = parentRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        
        Contact contact = parent.getContact();
        if (contact == null) {
             contact = contactService.create(parentSaveDto.contactCreate());
        } else {
             // Ideally update contact, but for now we can create new if needed or just update fields if ContactService supports it.
             // Since ContactService.create returns new contact, let's assume we replace it or we should update it.
             // For simplicity and safety against nulls in DTO, let's create new one as before but attach to existing parent.
             // Or better, if ContactService has update, use it. But it doesn't seem to have update exposed here easily.
             // Let's stick to creating new one for now as per original logic, but update fields on EXISTING parent object.
             contact = contactService.create(parentSaveDto.contactCreate());
        }

        parent.setFirstName(parentSaveDto.firstName());
        parent.setLastName(parentSaveDto.lastName());
        parent.setPatronymic(parentSaveDto.patronymic());
        parent.setRole(role);
        parent.setContact(contact);
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
        Parent parent = parentRepo.findByIdParent(id);
        if (parent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Id not found");
        }
        return parent;

    }

    @Override
    public List<Parent> findAll(List<Long> parents) {
        if (parents == null || parents.isEmpty()) {
            return List.of();
        }
        return parentRepo.findAllParents(parents);
    }

    @Override
    public List<Object> findChildrenByParentId(Long parentId) {
        Parent parent = parentRepo.findByIdParent(parentId);
        if (parent == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent not found");
        }
        List<kg.mega.kindergarten.models.Child> children = childRepo.findByParentId(parentId);
        return new java.util.ArrayList<>(children);
    }
}
