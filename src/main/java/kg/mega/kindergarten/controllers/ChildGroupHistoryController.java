package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/childGroupHistory")
public class ChildGroupHistoryController implements CRUDController<ChildGroupHistoryDto, ChildGroupHistoryCreateDto, ChildGroupHistory> {

    @Override
    public ChildGroupHistoryDto create(ChildGroupHistoryCreateDto childGroupHistoryCreateDto) {
        return null;
    }

    @Override
    public ChildGroupHistoryDto update(ChildGroupHistoryDto childGroupHistoryDto, Delete delete) {
        return null;
    }

    @Override
    public ChildGroupHistoryDto delete(Long id) {
        return null;
    }

    @Override
    public List<ChildGroupHistoryDto> allList(int page, int size) {
        return List.of();
    }



    @Override
    public ChildGroupHistory findById(Long id) {
        return null;
    }
}
