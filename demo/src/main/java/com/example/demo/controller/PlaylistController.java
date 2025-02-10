package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.model.Users;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UsersRepository usersRepository;

    /**
     * GET /api/playlist — получить все плейлисты текущего пользователя
     */
    @GetMapping
    public List<PlaylistResponseDTO> getAll(Authentication auth) throws Exception {
        String email = auth.getName();
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Пользователь не найден"));

        // Все плейлисты этого пользователя
        List<Playlist> playlists = playlistRepository.findByUser(user);

        // Преобразуем в DTO
        return playlists.stream()
                .map(PlaylistResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * POST /api/playlist — создать новый плейлист для текущего пользователя
     */
    @PostMapping
    public PlaylistResponseDTO createPlaylist(@RequestBody PlaylistDTO dto, Authentication auth) throws Exception {
        String email = auth.getName();
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Пользователь не найден"));

        Playlist playlist = new Playlist();
        playlist.setName(dto.getName());
        playlist.setDescription(dto.getDescription());
        playlist.setCoverImage("/resources/default_playlist.png"); // дефолтная обложка
        playlist.setCreatedAt(LocalDateTime.now());
        playlist.setUser(user);
        playlist.setSongsCount(0);

        Playlist savedPlaylist = playlistRepository.save(playlist);

        return new PlaylistResponseDTO(savedPlaylist);
    }

    /**
     * GET /api/playlist/{id} — получить детали плейлиста (принадлежащего текущему пользователю)
     */
    @GetMapping("/{id}")
    public Playlist getOne(@PathVariable Long id, Authentication auth) throws Exception {
        String email = auth.getName();
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Пользователь не найден"));

        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new Exception("Плейлист не найден"));

        // Проверка прав доступа
        if (!playlist.getUser().getId().equals(user.getId())) {
            throw new Exception("Доступ к этому плейлисту запрещен");
        }

        return playlist;
    }

    // Вспомогательный класс для запроса
    public static class PlaylistDTO {
        private String name;
        private String description;

        // геттеры/сеттеры
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }
}
