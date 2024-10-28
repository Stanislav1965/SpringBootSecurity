package ru.netology.springbootsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Настройка доступа к конечным точкам
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/authorize").hasRole("USER")
                        .requestMatchers("/users").hasRole("ADMIN")
                        .requestMatchers("/hello").permitAll())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                ).formLogin(withDefaults());
        return http.build();
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Пользователи системы anton, admin
    @Bean
    public UserDetailsService users() {
        UserDetails userAnton =
                User.withUsername("anton")
                        .password(passwordEncoder().encode("12345"))
                        .roles("USER")
                        .build();
        UserDetails userAdmin =
                User.withUsername("admin")
                        .password(passwordEncoder().encode("qwerty"))
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(userAnton, userAdmin);
    }
}
