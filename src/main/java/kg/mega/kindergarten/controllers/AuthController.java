package kg.mega.kindergarten.controllers;

import kg.mega.kindergarten.enums.RegisterRole;
import kg.mega.kindergarten.models.AppUser;
import kg.mega.kindergarten.models.dtos.RegistrationDto;
import kg.mega.kindergarten.services.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AppUserService userService;

    public AuthController(AppUserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDto dto) {
        AppUser appUser = userService.register(dto);

        return ResponseEntity.ok("Успешная регистрация. Роль будет назначена администратором.");
    }
    @PutMapping("/admin/set-role")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<String> setRole(@RequestParam Long userId, @RequestParam RegisterRole role) {
        AppUser appUser = userService.changeRole(userId, role);

        return ResponseEntity.ok("Роль успешно назначена");
    }
    @GetMapping("/admin/getAll")
    public List<AppUser> getAll() {
        return userService.getAll();

    }

}

