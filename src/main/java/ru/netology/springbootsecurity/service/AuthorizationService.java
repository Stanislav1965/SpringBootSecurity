package ru.netology.springbootsecurity.service;

import org.springframework.stereotype.Service;
import ru.netology.springbootsecurity.advice.InvalidCredentials;
import ru.netology.springbootsecurity.advice.UnauthorizedUser;
import ru.netology.springbootsecurity.domain.Authorities;
import ru.netology.springbootsecurity.model.Users;
import ru.netology.springbootsecurity.repository.UsersRepository;

import java.util.List;

@Service
public class AuthorizationService {
    private final UsersRepository usersRepository;

    public AuthorizationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Authorities> getAuthorities(String user, String password) {
        if (isEmpty(user) || isEmpty(password)) {
            throw new InvalidCredentials("User name or password is empty");
        }
        List<Authorities> userAuthorities = usersRepository.getUserAuthorities(user, password);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }

    public List<Users> getAll() {
        return usersRepository.getAll();
    }

}
