package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.BillResponseDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistorySaveDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDebtDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.services.BillService;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/child-group-history")
public class ChildGroupHistoryController implements CRUDController<ChildGroupHistoryDto, ChildGroupHistorySaveDto, ChildGroupHistory> {
    private final ChildGroupHistoryService childGroupHistoryService;
    private final BillService billService;

    public ChildGroupHistoryController(ChildGroupHistoryService childGroupHistoryService, BillService billService) {
        this.childGroupHistoryService = childGroupHistoryService;
        this.billService = billService;
    }

    @Operation(summary = "Создать запись истории группы ребенка")
    public ChildGroupHistoryDto create(ChildGroupHistorySaveDto childGroupHistoryCreateDto) {
        return childGroupHistoryService.create(childGroupHistoryCreateDto);
    }

    @Override
    @Operation(summary = "Обновить запись истории группы ребенка")
    public ChildGroupHistoryDto update(Long id, ChildGroupHistorySaveDto childGroupHistorySaveDto, Delete delete) {
        return childGroupHistoryService.update(id,childGroupHistorySaveDto, delete);

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
    public ChildGroupHistoryDebtDto findDebtByChildId(@RequestParam Long childId) {
        return childGroupHistoryService.findDebtByChildId(childId);
    }

    @Operation(summary = "Создать счет на оплату для ребенка")
    @PostMapping("/bill")
    public BillResponseDto generateBill(@RequestParam Long childId) {
        return billService.generateBill(childId);
    }

    @Operation(summary = "Получить статус счета по ID счета")
    @GetMapping("/status")
    public BillResponseDto getBillStatus(@RequestParam Long billId) {
        return billService.getBillStatus(billId);
    }
}
