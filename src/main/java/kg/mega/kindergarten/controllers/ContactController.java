package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.dtos.ContactCreateDto;
import kg.mega.kindergarten.models.dtos.ContactDto;
import kg.mega.kindergarten.services.ContactService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/contact")
public class ContactController implements CRUDController<ContactDto, ContactCreateDto, Contact> {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ContactDto create(ContactCreateDto contactCreateDto) {
        return contactService.create(contactCreateDto) ;
    }

    @Override
    public ContactDto update(ContactDto contactDto, Delete delete) {
        return contactService.update(contactDto, delete);
    }

    @Override
    public ContactDto delete(Long id) {
        return contactService.delete(id);
    }

    @Override
    public List<ContactDto> allList(int page, int size) {
        return contactService.findAllList(page,size);
    }



    @Override
    public Contact findById(Long id) {
        return contactService.findById(id);
    }
}
