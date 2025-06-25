package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDebtDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/child-group-history")
public class ChildGroupHistoryController implements CRUDController<ChildGroupHistoryDto, ChildGroupHistoryCreateDto, ChildGroupHistory> {
    private final ChildGroupHistoryService childGroupHistoryService;

    public ChildGroupHistoryController(ChildGroupHistoryService childGroupHistoryService) {
        this.childGroupHistoryService = childGroupHistoryService;
    }

    @Operation(summary = "Создать запись истории группы ребенка")
    public ChildGroupHistoryDto create(ChildGroupHistoryCreateDto childGroupHistoryCreateDto) {
        return childGroupHistoryService.create(childGroupHistoryCreateDto);
    }

    @Override
    @Operation(summary = "Обновить запись истории группы ребенка")
    public ChildGroupHistoryDto update(ChildGroupHistoryDto childGroupHistoryDto, Delete delete) {
        return childGroupHistoryService.update(childGroupHistoryDto, delete);
    }

    @Override
    @Operation(summary = "Удалить запись истории группы ребенка по ID")
    public ChildGroupHistoryDto delete(Long id) {
        return childGroupHistoryService.delete(id);
    }

    @Override
    @Operation(summary = "Получить список всех записей истории групп")
    public List<ChildGroupHistory> allList(int page, int size) {
        return childGroupHistoryService.findAllList(page, size);
    }

    @Override
    @Operation(summary = "Найти запись истории группы ребенка по ID")
    public ChildGroupHistory findById(Long id) {
        return childGroupHistoryService.findById(id);
    }

    @Operation(summary = "Получить задолженность ребенка по ID ребенка")
    @GetMapping("/debt")
    public ChildGroupHistoryDebtDto findDebtByChildId(Long childId) {
        return childGroupHistoryService.findDebtByChildId(childId);
    }
}
