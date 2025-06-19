package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.dtos.ChildCreateDto;
import kg.mega.kindergarten.models.dtos.ChildDto;
import kg.mega.kindergarten.services.ChildService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/child")
public class ChildController implements CRUDController<ChildDto, ChildCreateDto, Child> {
    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping("/create")
    public ChildDto create(ChildCreateDto childCreateDto) {
        return childService.create(childCreateDto) ;
    }


    @Override
    public ChildDto update(ChildDto childDto, Delete delete) {
        return childService.update(childDto, delete);
    }

    @Override
    public ChildDto delete(Long id) {
        return childService.delete(id);
    }

    @Override
    public List<Child> allList(int page, int size) {
        return childService.findAllList(page,size);
    }



    @Override
    public Child findById(Long id) {
        return childService.findById(id);
    }
}