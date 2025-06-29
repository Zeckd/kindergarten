package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.dtos.ChildSaveDto;
import kg.mega.kindergarten.models.dtos.ChildDto;
import kg.mega.kindergarten.services.ChildService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/child")
public class ChildController implements CRUDController<ChildDto, ChildSaveDto, Child> {
    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @Operation(summary = "Создать нового ребенка")
    public ChildDto create(ChildSaveDto childCreateDto) {
        return childService.create(childCreateDto);
    }

    @Override
    @Operation(summary = "Обновить данные ребенка")

    public ChildDto update(Long id, ChildSaveDto childSaveDto, Delete delete) {
        return childService.update(id,childSaveDto, delete);
    }

    @Override
    @Operation(summary = "Удалить ребенка по ID")
    public ChildDto delete(Long id) {
        return childService.delete(id);
    }

    @Override
    @Operation(summary = "Получить список всех детей")
    public List<Child> allList(int page, int size) {
        return childService.findAllList(page, size);
    }

    @Override
    @Operation(summary = "Найти ребенка по ID")
    public Child findById(Long id) {
        return childService.findById(id);
    }
}
