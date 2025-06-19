package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentCreateDto;
import kg.mega.kindergarten.models.dtos.ParentDto;

import java.util.List;

public interface ParentService {
    ParentDto create(ParentCreateDto parentCreateDto, Role role);

    ParentDto update(ParentDto parentDto, Delete delete);

    ParentDto delete(Long id);

    List<Parent> findAllList(int page, int size);

    Parent findById(Long id);

    List<Parent> findAll(List<Long> parents);
}
