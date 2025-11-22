package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Group;
import kg.mega.kindergarten.models.dtos.GroupSaveDto;
import kg.mega.kindergarten.models.dtos.GroupDto;
import kg.mega.kindergarten.services.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController implements CRUDController<GroupDto, GroupSaveDto, Group> {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    @PostMapping("/create")
    @Operation(summary = "Создать новую группу")
    public GroupDto create(@RequestBody GroupSaveDto groupCreateDto) {
        return groupService.create(groupCreateDto);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "Обновить данные группы")
    public GroupDto update(@RequestParam Long id, @RequestBody GroupSaveDto groupSaveDto, @RequestParam Delete delete) {
        return groupService.update(id,groupSaveDto, delete);

    }


    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить группу по ID")
    public GroupDto delete(@RequestParam Long id) {
        return groupService.delete(id);
    }

    @Override
    @GetMapping("/get-list")
    @Operation(summary = "Получить список всех групп")
    public List<Group> allList(@RequestParam int page, @RequestParam int size) {
        return groupService.findAllList(page, size);
    }

    @Override
    @GetMapping("/find-by-id")
    @Operation(summary = "Найти группу по ID")
    public Group findById(@RequestParam Long id) {
        return groupService.findById(id);
    }

    @Operation(summary = "Добавить учителя, ассистента или ребенка в группу")
    @PutMapping("/add-teacher-assistant-child")
    public GroupDto addTeacherOrAssistant(
            @RequestParam Long id,
            @RequestParam(required = false) Long teacherOrAssistantId,
            @RequestParam(required = false) Long childId) {
        return groupService.addTeacherOrAssistantAndChild(id, teacherOrAssistantId, childId);
    }

    @Operation(summary = "Удалить учителя из группы")
    @PutMapping("/remove-teacher")
    public GroupDto removeTeacher(@RequestParam Long id) {
        return groupService.removeTeacher(id);
    }

    @Operation(summary = "Удалить ассистента из группы")
    @PutMapping("/remove-assistant")
    public GroupDto removeAssistant(@RequestParam Long id) {
        return groupService.removeAssistant(id);
    }

    @Operation(summary = "Удалить ребенка из группы")
    @PutMapping("/remove-child")
    public GroupDto removeChild(
            @RequestParam Long groupId,
            @RequestParam Long childId) {
        return groupService.removeChildFromGroup(groupId, childId);
    }
}
