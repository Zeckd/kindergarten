package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.dtos.ChildSaveDto;
import kg.mega.kindergarten.models.dtos.ChildDto;
import kg.mega.kindergarten.services.ChildService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/child")
public class ChildController implements CRUDController<ChildDto, ChildSaveDto, Child> {
    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @Override
    @PostMapping("/create")
    @Operation(summary = "Создать нового ребенка")
    public ChildDto create(@RequestBody ChildSaveDto childCreateDto) {
        System.out.println("Creating child: " + childCreateDto);
        return childService.create(childCreateDto);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "Обновить данные ребенка")
    public ChildDto update(@RequestParam Long id, @RequestBody ChildSaveDto childSaveDto, @RequestParam Delete delete) {
        return childService.update(id,childSaveDto, delete);
    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить ребенка по ID")
    public ChildDto delete(@RequestParam Long id) {
        return childService.delete(id);
    }

    @Override
    @GetMapping("/get-list")
    @Operation(summary = "Получить список всех детей")
    public List<Child> allList(@RequestParam int page, @RequestParam int size) {
        return childService.findAllList(page, size);
    }

    @Override
    @GetMapping("/find-by-id")
    @Operation(summary = "Найти ребенка по ID")
    public Child findById(@RequestParam Long id) {
        return childService.findById(id);
    }

    @Operation(summary = "Получить список детей по ID группы")
    @GetMapping("/by-group")
    public List<Child> getChildrenByGroup(@RequestParam Long groupId) {
        return childService.findByGroupId(groupId);
    }

    @Operation(summary = "Получить список детей по ID родителя")
    @GetMapping("/by-parent")
    public List<Child> getChildrenByParent(@RequestParam Long parentId) {
        return childService.findByParentId(parentId);
    }
}
