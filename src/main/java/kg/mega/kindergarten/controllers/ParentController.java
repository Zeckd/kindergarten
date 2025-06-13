package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentCreateDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/parent")
public class ParentController implements CRUDController<ParentDto, ParentCreateDto, Parent> {
    @Override
    public ParentDto create(ParentCreateDto parentCreateDto) {
        return null;
    }

    @Override
    public ParentDto update(ParentDto parentDto, Delete delete) {
        return null;
    }

    @Override
    public ParentDto delete(Long id) {
        return null;
    }

    @Override
    public List<ParentDto> allList(int page, int size) {
        return List.of();
    }


    @Override
    public Parent findById(Long id) {
        return null;
    }
}
