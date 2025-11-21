package kg.mega.kindergarten.mappers;

import kg.mega.kindergarten.models.Contact;
import kg.mega.kindergarten.models.dtos.ContactSaveDto;
import kg.mega.kindergarten.models.dtos.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact contactSaveDtoToContact(ContactSaveDto contactSaveDto);


    ContactDto contactToContactDto(Contact contact);



}
