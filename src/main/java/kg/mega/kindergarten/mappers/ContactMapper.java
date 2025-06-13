package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.dtos.ContactCreateDto;
import kg.mega.kindergarten.models.dtos.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact contactCreateDtoToContact(ContactCreateDto contactCreateDto);

    ContactDto contactToContactDto(Contact contact);

    Contact contactDtoToContact(ContactDto contactDto);

    List<ContactDto> contactToContactDto(List<Contact> contact);

}
