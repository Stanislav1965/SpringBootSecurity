package ru.netology.springbootsecurity.repository;

import ru.netology.springbootsecurity.domain.Authorities;
import ru.netology.springbootsecurity.model.Users;
import java.util.List;


public interface UsersRepository {

    List<Authorities> getUserAuthorities(String user, String password);

    List<Users> getAll();
}
