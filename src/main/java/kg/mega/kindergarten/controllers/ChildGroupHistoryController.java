package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.ChildGroupHistory;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryCreateDto;
import kg.mega.kindergarten.models.dtos.ChildGroupHistoryDto;
import kg.mega.kindergarten.services.ChildGroupHistoryService;
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

    @PostMapping("/create")
    public ChildGroupHistoryDto create(ChildGroupHistoryCreateDto childGroupHistoryCreateDto) {
        return childGroupHistoryService.create(childGroupHistoryCreateDto) ;
    }


    @Override
    public ChildGroupHistoryDto update(ChildGroupHistoryDto childGroupHistoryDto, Delete delete) {
        return childGroupHistoryService.update(childGroupHistoryDto, delete);
    }

    @Override
    public ChildGroupHistoryDto delete(Long id) {
        return childGroupHistoryService.delete(id);
    }

    @Override
    public List<ChildGroupHistory> allList(int page, int size) {
        return childGroupHistoryService.findAllList(page,size);
    }



    @Override
    public ChildGroupHistory findById(Long id) {
        return childGroupHistoryService.findById(id);
    }
}
