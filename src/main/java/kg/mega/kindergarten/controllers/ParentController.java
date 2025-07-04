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

    @Operation(summary = "Создать нового родителя с ролью")
    public ParentDto create(
           ParentSaveDto parentCreateDto,
            @Parameter(description = "Роль родителя", required = true)
            Role role) {
        return parentService.create(parentCreateDto, role);
    }

    @Override
    @Operation(summary = "Обновить данные родителя")
    public ParentDto update(Long id, ParentSaveDto parentSaveDto,Role role, Delete delete) {
        return parentService.update(id ,parentSaveDto,role, delete);
    }

    @Override
    @Operation(summary = "Удалить родителя по ID")
    public ParentDto delete(Long id) {
        return parentService.delete(id);
    }

    @Override
    @Operation(summary = "Получить список всех родителей")
    public List<Parent> allList(int page, int size) {
        return parentService.findAllList(page, size);
    }

    @Override
    @Operation(summary = "Найти родителя по ID")
    public Parent findById(Long id) {
        return parentService.findById(id);
    }
}
