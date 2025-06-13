package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.dtos.GroupCreateDto;
import kg.mega.kindergarten.models.dtos.GroupDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/group")
public class GroupController implements CRUDController<GroupDto, GroupCreateDto, Group> {
    @Override
    public GroupDto create(GroupCreateDto groupCreateDto) {
        return null;
    }

    @Override
    public GroupDto update(GroupDto groupDto, Delete delete) {
        return null;
    }

    @Override
    public GroupDto delete(Long id) {
        return null;
    }

    @Override
    public List<GroupDto> allList(int page, int size) {
        return List.of();
    }


    @Override
    public Group findById(Long id) {
        return null;
    }
}
