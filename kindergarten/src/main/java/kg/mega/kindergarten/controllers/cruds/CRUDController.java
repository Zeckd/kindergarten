package kg.mega.kindergarten.controllers.cruds;

import jakarta.validation.Valid;
import kg.mega.kindergarten.enums.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CRUDController <Dto, CreateOrUpdateDto, Entity>{
    @PostMapping("/create")
    Dto create(@Valid @RequestBody CreateOrUpdateDto dto);
    @PutMapping("/update")
    Dto update(@RequestParam Long id, @Valid @RequestBody CreateOrUpdateDto dto, @RequestParam Delete delete);
    @DeleteMapping("/delete")
    Dto delete(@RequestParam Long id);
    @GetMapping("/get-list")
    List<Entity> allList(@RequestParam int page, @RequestParam int size);
    @GetMapping("/find-by-id")
    Entity findById(@RequestParam Long id);

}
