package kg.mega.kindergarten.controllers.cruds;

import jakarta.validation.Valid;
import kg.mega.kindergarten.enums.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CRUDControllerWithStatus <Dto, CreateDto, Entity, Status> {
    @PostMapping("/create")
    Dto create(@Valid @RequestBody CreateDto dto, @RequestParam Status status);

    @PutMapping("/update")
    Dto update(@Valid @RequestBody Dto dto, @RequestParam Delete delete);

    @DeleteMapping("/delete")
    Dto delete(@RequestParam Long id);

    @GetMapping("/get-list")
    List<Entity> allList(@RequestParam int page, @RequestParam int size);

    @GetMapping("/find-by-id")
    Entity findById(@RequestParam Long id);
}
