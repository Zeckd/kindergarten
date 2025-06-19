package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.dtos.ChildCreateDto;
import kg.mega.kindergarten.models.dtos.ChildDto;

import java.util.List;

public interface ChildService {
    ChildDto create(ChildCreateDto childCreateDto);

    ChildDto update(ChildDto childDto, Delete delete);

    ChildDto delete(Long id);

    List<Child> findAllList(int page, int size);

    Child findById(Long id);

    List<Child> findByIdToList(Long childId);
}
