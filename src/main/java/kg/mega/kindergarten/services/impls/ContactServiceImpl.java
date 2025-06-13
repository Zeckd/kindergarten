package kg.mega.kindergarten.services.impls;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.mappers.ContactMapper;
import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.dtos.ContactDto;
import kg.mega.kindergarten.models.dtos.ContactCreateDto;
import kg.mega.kindergarten.repositories.ContactRepo;
import kg.mega.kindergarten.services.ContactService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ContactServiceImpl implements ContactService {
    private final ContactRepo contactRepo;

    public ContactServiceImpl(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public ContactDto create(ContactCreateDto contactCreateDto) {
        Contact contact = ContactMapper.INSTANCE.contactCreateDtoToContact(contactCreateDto);
        contact = contactRepo.save(contact);
        return ContactMapper.INSTANCE.contactToContactDto(contact) ;
    }

    @Override
    public ContactDto update(ContactDto contactDto, Delete delete) {
        Contact contact = ContactMapper.INSTANCE.contactDtoToContact(contactDto);
        contact.setDelete(delete);
        contact = contactRepo.save(contact);


        return ContactMapper.INSTANCE.contactToContactDto(contact);
    }

    @Override
    public List<ContactDto> findAllList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return contactRepo.findAllList(pageable);

    }

    @Override
    public ContactDto delete(Long id) {
        Contact contact = contactRepo.findById(id).orElseThrow();
        contactRepo.deleteById(id);
        return ContactMapper.INSTANCE.contactToContactDto(contact) ;

    }




    @Override
    public Contact findById(Long id) {

        return contactRepo.findByIdContact(id);

    }
}
