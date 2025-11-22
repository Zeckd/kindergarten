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

    @Override
    @PostMapping("/create")
    @Operation(summary = "Создать запись истории группы ребенка")
    public ChildGroupHistoryDto create(@RequestBody ChildGroupHistorySaveDto childGroupHistoryCreateDto) {
        return childGroupHistoryService.create(childGroupHistoryCreateDto);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "Обновить запись истории группы ребенка")
    public ChildGroupHistoryDto update(@RequestParam Long id, @RequestBody ChildGroupHistorySaveDto childGroupHistorySaveDto, @RequestParam Delete delete) {
        return childGroupHistoryService.update(id,childGroupHistorySaveDto, delete);

    }


    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить запись истории группы ребенка по ID")
    public ChildGroupHistoryDto delete(@RequestParam Long id) {
        return childGroupHistoryService.delete(id);
    }

    @Override
    @GetMapping("/get-list")
    @Operation(summary = "Получить список всех записей истории групп")
    public List<ChildGroupHistory> allList(@RequestParam int page, @RequestParam int size) {
        return childGroupHistoryService.findAllList(page, size);
    }

    @Override
    @GetMapping("/find-by-id")
    @Operation(summary = "Найти запись истории группы ребенка по ID")
    public ChildGroupHistory findById(@RequestParam Long id) {
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

    @Operation(summary = "Создать счет на оплату для ребенка за период")
    @PostMapping("/bill-period")
    public BillResponseDto generateBillForPeriod(@RequestParam Long childId, @RequestParam String period) {
        return billService.generateBillForPeriod(childId, period);
    }

    @Operation(summary = "Получить статус счета по ID счета")
    @GetMapping("/status")
    public BillResponseDto getBillStatus(@RequestParam Long billId) {
        return billService.getBillStatus(billId);
    }
}
