package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.controllers.cruds.CRUDController;
import kg.mega.kindergarten.controllers.cruds.CRUDControllerWithStatus;
import kg.mega.kindergarten.enums.Delete;
import kg.mega.kindergarten.enums.Role;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.Parent;
import kg.mega.kindergarten.models.dtos.ParentCreateDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import kg.mega.kindergarten.models.dtos.ParentCreateDto;
import kg.mega.kindergarten.models.dtos.ParentDto;
import kg.mega.kindergarten.services.ParentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/parent")
public class ParentController implements CRUDControllerWithStatus<ParentDto, ParentCreateDto, Parent, Role> {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping("/create")
    public ParentDto create( ParentCreateDto parentCreateDto, Role role) {
        return parentService.create(parentCreateDto, role) ;
    }


    @Override
    public ParentDto update(ParentDto parentDto, Delete delete) {
        return parentService.update(parentDto, delete);
    }

    @Override
    public ParentDto delete(Long id) {
        return parentService.delete(id);
    }

    @Override
    public List<Parent> allList(int page, int size) {
        return parentService.findAllList(page,size);
    }



    @Override
    public Parent findById(Long id) {
        return parentService.findById(id);
    }
}
