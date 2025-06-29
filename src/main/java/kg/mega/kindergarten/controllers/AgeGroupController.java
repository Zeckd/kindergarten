package kg.mega.kindergarten.controllers;

import io.swagger.v3.oas.annotations.Operation;
import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.AgeGroup;
import kg.mega.kindergarten.models.dtos.AgeGroupSaveDto;
import kg.mega.kindergarten.models.dtos.AgeGroupDto;
import kg.mega.kindergarten.services.AgeGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/age-group")
public class AgeGroupController implements CRUDController<AgeGroupDto, AgeGroupSaveDto, AgeGroup> {
    private final AgeGroupService ageGroupService;

    public AgeGroupController(AgeGroupService ageGroupService) {
        this.ageGroupService = ageGroupService;
    }

    @Override
    @Operation(summary = "Создать новую возрастную группу")
    public AgeGroupDto create(AgeGroupSaveDto ageGroupCreateDto) {
        return ageGroupService.create(ageGroupCreateDto);
    }

    @Override
    @Operation(summary = "Обновить существующую возрастную группу")
    public AgeGroupDto update(Long id, AgeGroupSaveDto ageGroupSaveDto, Delete delete) {
        return ageGroupService.update(id, ageGroupSaveDto, delete);

    }

    @Override
    @Operation(summary = "Удалить возрастную группу по ID")
    public AgeGroupDto delete(Long id) {
        return ageGroupService.delete(id);
    }

    @Override
    @Operation(summary = "Получить список всех возрастных групп")
    public List<AgeGroup> allList(int page, int size) {
        return ageGroupService.allList(page, size);
    }

    @Override
    @Operation(summary = "Найти возрастную группу по ID")
    public AgeGroup findById(Long id) {
        return ageGroupService.findById(id);
    }
}
