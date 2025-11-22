package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupSaveDto;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;
import kg.mega.kindergarten.services.AgeGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/age-group")
public class AgeGroupController implements CRUDController<AgeGroupDto, AgeGroupSaveDto, AgeGroup> {
    private final AgeGroupService ageGroupService;

    public AgeGroupController(AgeGroupService ageGroupService) {
        this.ageGroupService = ageGroupService;
    }

    @Override
    @PostMapping("/create")
    @Operation(summary = "Создать новую возрастную группу")
    public AgeGroupDto create(@RequestBody AgeGroupSaveDto ageGroupCreateDto) {
        return ageGroupService.create(ageGroupCreateDto);
    }

    @Override
    @PutMapping("/update")
    @Operation(summary = "Обновить существующую возрастную группу")
    public AgeGroupDto update(@RequestParam Long id, @RequestBody AgeGroupSaveDto ageGroupSaveDto, @RequestParam Delete delete) {
        return ageGroupService.update(id, ageGroupSaveDto, delete);

    }

    @Override
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить возрастную группу по ID")
    public AgeGroupDto delete(@RequestParam Long id) {
        return ageGroupService.delete(id);
    }

    @Override
    @GetMapping("/get-list")
    @Operation(summary = "Получить список всех возрастных групп")
    public List<AgeGroup> allList(@RequestParam int page, @RequestParam int size) {
        return ageGroupService.allList(page, size);
    }

    @Override
    @GetMapping("/find-by-id")
    @Operation(summary = "Найти возрастную группу по ID")
    public AgeGroup findById(@RequestParam Long id) {
        return ageGroupService.findById(id);
    }
}
