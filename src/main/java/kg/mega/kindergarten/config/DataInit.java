package kg.mega.kindergarten.config;


import kg.mega.kindergarten.enums.RegisterRole;
import kg.mega.kindergarten.models.AppUser;
import kg.mega.kindergarten.repositories.AppUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class DataInit {

    @Bean
    public CommandLineRunner createDefaultAdmin(AppUserRepo userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            String adminPassword = "admin123";

            if (userRepo.findByUsername(adminUsername).isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(RegisterRole.ADMIN);

                userRepo.save(admin);
                System.out.println("✅ Администратор создан: username=admin, password=admin123");
            } else {
                System.out.println("ℹ️ Администратор уже существует");
            }
        };
    }
}
