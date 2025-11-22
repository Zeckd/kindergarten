package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentSaveDto;
import kg.mega.kindergarten.models.dtos.ParentDto;

import java.util.List;

public interface ParentService {
    ParentDto create(ParentSaveDto parentCreateDto, Role role);


    ParentDto delete(Long id);

    List<Parent> findAllList(int page, int size);

    Parent findById(Long id);

    List<Parent> findAll(List<Long> parents);

    ParentDto update(Long id, ParentSaveDto parentSaveDto,Role role, Delete delete);

    List<Object> findChildrenByParentId(Long parentId);
}
