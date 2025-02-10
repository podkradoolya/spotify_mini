package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    /**
     * Получение информации о текущем пользователе.
     */
    @GetMapping("/me")
    public Users getCurrentUserProfile() {
        return userService.getCurrentUser();
    }






    // Другие методы по необходимости
}
