package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.dtos.*;
import kg.mega.kindergarten.models.dtos.GroupCreateDto;
import kg.mega.kindergarten.models.dtos.GroupDto;
import kg.mega.kindergarten.services.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/group")
public class GroupController implements CRUDController<GroupDto, GroupCreateDto, Group> {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public GroupDto create(GroupCreateDto groupCreateDto) {
        return groupService.create(groupCreateDto) ;
    }


    @Override
    public GroupDto update(GroupDto groupDto, Delete delete) {
        return groupService.update(groupDto, delete);
    }

    @Override
    public GroupDto delete(Long id) {
        return groupService.delete(id);
    }

    @Override
    public List<Group> allList(int page, int size) {
        return groupService.findAllList(page,size);
    }



    @Override
    public Group findById(Long id) {
        return groupService.findById(id);
    }
    @PutMapping("/add-teacher-assistant-child")
    public GroupDto addTeacherOrAssistant(@RequestParam Long id,@RequestParam(required = false) Long teacherOrAssistantId, @RequestParam(required = false) Long childId) {
        return groupService.addTeacherOrAssistantAndChild(id, teacherOrAssistantId, childId);
    }


}
