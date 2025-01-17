package ru.netology.springbootsecurity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.springbootsecurity.advice.InvalidCredentials;
import ru.netology.springbootsecurity.advice.UnauthorizedUser;
import ru.netology.springbootsecurity.domain.Authorities;
import ru.netology.springbootsecurity.model.Users;
import ru.netology.springbootsecurity.service.AuthorizationService;

import java.util.List;

@RestController
public class AuthorizationController {
    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        return service.getAuthorities(user, password);
    }

    @GetMapping("/users")
    public List<Users> getAll() {
        return service.getAll();
    }

    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<String> invHandler(InvalidCredentials e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public ResponseEntity<String> unHandler(UnauthorizedUser e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
