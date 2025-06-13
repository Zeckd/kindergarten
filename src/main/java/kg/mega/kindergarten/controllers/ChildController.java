package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Child;
import kg.mega.kindergarten.models.dtos.ChildCreateDto;
import kg.mega.kindergarten.models.dtos.ChildDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/child")
public class ChildController implements CRUDController<ChildDto, ChildCreateDto, Child> {
    @Override
    public ChildDto create(ChildCreateDto childCreateDto) {
        return null;
    }

    @Override
    public ChildDto update(ChildDto childDto, Delete delete) {
        return null;
    }

    @Override
    public ChildDto delete(Long id) {
        return null;
    }

    @Override
    public List<ChildDto> allList(int page, int size) {
        return List.of();
    }



    @Override
    public Child findById(Long id) {
        return null;
    }
}