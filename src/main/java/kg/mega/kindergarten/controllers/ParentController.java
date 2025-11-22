package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kg.mega.kindergarten.controllers.cruds.CRUDControllerWithStatus;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentSaveDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import kg.mega.kindergarten.services.ParentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parent")
public class ParentController implements CRUDControllerWithStatus<ParentDto, ParentSaveDto, Parent, Role> {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @Override
    @PostMapping("/create")
    @Operation(summary = "Создать нового родителя с ролью")
    public ParentDto create(
           @RequestBody ParentSaveDto parentCreateDto,
            @Parameter(description = "Роль родителя", required = true)
            @RequestParam Role role) {
        return parentService.create(parentCreateDto, role);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "Обновить данные родителя")
    public ParentDto update(@RequestParam Long id, @RequestBody ParentSaveDto parentSaveDto, @RequestParam Role role, @RequestParam Delete delete) {
        return parentService.update(id ,parentSaveDto,role, delete);
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить родителя по ID")
    public ParentDto delete(@RequestParam Long id) {
        return parentService.delete(id);
    }

    @Override
    @GetMapping("/get-list")
    @Operation(summary = "Получить список всех родителей")
    public List<Parent> allList(@RequestParam int page, @RequestParam int size) {
        return parentService.findAllList(page, size);
    }

    @Override
    @GetMapping("/find-by-id")
    @Operation(summary = "Найти родителя по ID")
    public Parent findById(@RequestParam Long id) {
        return parentService.findById(id);
    }

    @Operation(summary = "Получить список детей родителя")
    @GetMapping("/children")
    public List<Object> getParentChildren(@RequestParam Long parentId) {
        return parentService.findChildrenByParentId(parentId);
    }
}
