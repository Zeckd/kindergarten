package kg.mega.kindergarten.config;

import kg.mega.kindergarten.repositories.AppUserRepo;
import kg.mega.kindergarten.services.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final AppUserDetailsService appUserDetailsService;

    public SecurityConfig(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth


                        .requestMatchers("/api/auth/register")
                        .permitAll()

                        .requestMatchers("/api/teacher/get-list")
                        .permitAll()
                        .requestMatchers("/api/age-group/get-list")
                        .permitAll()



                        .requestMatchers("/api/child/get-list")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/child/find-by-id")
                        .hasAnyRole("TEACHER", "ADMIN", "PARENT")
                        .requestMatchers("/api/parent/create")
                        .hasAnyRole("PARENT", "ADMIN")
                        .requestMatchers("/api/group/get-list")
                        .hasAnyRole("TEACHER", "ADMIN", "PARENT")
                        .requestMatchers("/api/group/find-by-id")
                        .hasAnyRole("TEACHER", "ADMIN", "PARENT")
                        .requestMatchers("/api/payment/find-by-id")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/teacher/create")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/parent/get-list")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/**")
                        .hasRole("ADMIN")


                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }









    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}