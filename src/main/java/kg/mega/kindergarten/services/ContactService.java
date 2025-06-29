package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.dtos.ContactSaveDto;
import kg.mega.kindergarten.models.dtos.ContactDto;

import java.util.List;

public interface ContactService {
    Contact create(ContactSaveDto contactCreateDto);


    ContactDto update(ContactSaveDto contactSaveDto, Delete delete);

    ContactDto delete(Long id);

    List<Contact> findAllList(int page, int size);

    Contact findById(Long id);
}
