package com.example.demo.service;

import com.example.demo.model.Playlist;
import com.example.demo.model.Users;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Регистрация пользователя и создание плейлиста "Liked Songs"
     */
    @Transactional
    public Users registerUser(String nickname, String email, String password) throws Exception {
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email уже зарегистрирован.");
        }
        if (usersRepository.findByNickname(nickname).isPresent()) {
            throw new Exception("Nickname уже используется.");
        }

        // Создание нового пользователя
        Users user = new Users();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRegistrationDate(new Date());
        Users savedUser = usersRepository.save(user);

        // Создание плейлиста "Liked Songs"
        Playlist liked = new Playlist();
        liked.setName("Liked Songs");
        liked.setCreatedAt(LocalDateTime.now());
        liked.setUser(savedUser);
        liked.setCoverImage("/resources/default_playlist.png"); // Путь к дефолтной картинке
        liked.setDescription("Your default liked songs collection");
        liked.setSongsCount(0);

        playlistRepository.save(liked);

        return savedUser;
    }


    public Users getUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    /**
     * Получить текущего пользователя (например, по ID из контекста безопасности)
     */
    public Users getCurrentUser() {
        // Здесь можно добавить логику для получения ID текущего пользователя
        // Например, из контекста Spring Security
        Long currentUserId = 1L; // Замените на реальный ID текущего пользователя
        return getUserById(currentUserId);
    }

}
