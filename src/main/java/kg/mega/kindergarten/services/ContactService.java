package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.dtos.ContactCreateDto;
import kg.mega.kindergarten.models.dtos.ContactDto;

import java.util.List;

public interface ContactService {
    ContactDto create(ContactCreateDto contactCreateDto);


    ContactDto update(ContactDto contactDto, Delete delete);

    ContactDto delete(Long id);

    List<ContactDto> findAllList(int page, int size);

    Contact findById(Long id);
}
