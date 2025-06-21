package kg.mega.kindergarten.services;

import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDebtDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;

import java.util.List;

public interface ChildGroupHistoryService {
    ChildGroupHistoryDto create(ChildGroupHistoryCreateDto childGroupHistoryCreateDto);

    ChildGroupHistoryDto update(ChildGroupHistoryDto childGroupHistoryDto, Delete delete);

    ChildGroupHistoryDto delete(Long id);

    List<ChildGroupHistory> findAllList(int page, int size);

    ChildGroupHistory findById(Long id);

    ChildGroupHistoryDebtDto findDebtByChildId(Long childId);
}
