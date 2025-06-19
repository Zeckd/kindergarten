package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.dtos.GroupCreateDto;
import kg.mega.kindergarten.models.dtos.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto create(GroupCreateDto groupCreateDto);

    GroupDto update(GroupDto groupDto, Delete delete);

    GroupDto delete(Long id);

    List<Group> findAllList(int page, int size);

    Group findById(Long id);

    GroupDto addTeacherOrAssistantAndChild(Long groupId, Long teacherOrAssistantId, Long childId);

}
