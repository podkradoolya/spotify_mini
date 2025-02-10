package com.example.demo.controller;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * GET /auth/login — показать форму логина
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // login.html
    }

    /**
     * GET /auth/register — показать форму регистрации
     */
    @GetMapping("/register")
    public String registerForm() {
        return "register"; // register.html
    }

    /**
     * POST /auth/register — обработка регистрации
     * После регистрации — авто-логин и редирект на главную страницу
     */
    @PostMapping("/register")
    public String register(@RequestParam("username") String nickname,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {
        try {
            // Регистрация пользователя
            Users user = userService.registerUser(nickname, email, password);

            // Авто-логин
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Редирект на главную страницу
            return "redirect:/";
        } catch (Exception e) {
            String msg = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/auth/register?error=" + msg;
        }
    }
}
