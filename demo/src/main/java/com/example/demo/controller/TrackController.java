package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.model.Track;
import com.example.demo.model.Users;
import com.example.demo.repository.PlaylistRepository;
import com.example.demo.repository.TrackRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    /**
     * GET /api/tracks - Получить все треки
     */
    @GetMapping
    public List<TrackDTO> getAllTracks() {
        List<Track> tracks = trackRepository.findAll();
        return tracks.stream().map(TrackDTO::new).toList();
    }

    // DTO класс для ответа
    public static class TrackDTO {
        private Long id;
        private String title;
        private String artistName;
        private String duration;
        private String audioSrc;
        private String image;
        private String info; // Добавлено поле info

        public TrackDTO(Track track) {
            this.id = track.getId();
            this.title = track.getTitle();
            this.artistName = track.getArtist() != null ? track.getArtist().getName() : "Unknown";
            this.duration = track.getDuration();
            this.audioSrc = track.getFilePath();
            this.image = track.getImagePath();
            this.info = track.getDescription(); // Предполагается, что описание хранится в поле description
        }

        // Геттеры и сеттеры
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getArtistName() { return artistName; }
        public void setArtistName(String artistName) { this.artistName = artistName; }

        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }

        public String getAudioSrc() { return audioSrc; }
        public void setAudioSrc(String audioSrc) { this.audioSrc = audioSrc; }

        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }

        public String getInfo() { return info; }
        public void setInfo(String info) { this.info = info; }
    }

    @PostMapping("/{id}/favorite")
    public TrackResponseDTO toggleFavorite(@PathVariable Long id,
                                           @RequestBody FavoriteRequestDTO dto,
                                           Authentication auth) throws Exception {
        String email = auth.getName();
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Пользователь не найден"));

        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new Exception("Трек не найден"));

        // Получаем или создаём плейлист "Liked Songs"
        Playlist likedPlaylist = getOrCreateLikedSongsPlaylist(user);

        if (dto.isFavorite()) {
            // Добавляем трек в плейлист, если его там ещё нет
            if (!likedPlaylist.getTracks().contains(track)) {
                likedPlaylist.addTrack(track);
                playlistRepository.save(likedPlaylist);
            }
        } else {
            // Удаляем трек из плейлиста
            if (likedPlaylist.getTracks().contains(track)) {
                likedPlaylist.removeTrack(track);
                playlistRepository.save(likedPlaylist);
            }
        }

        return new TrackResponseDTO(track);
    }

    private Playlist getOrCreateLikedSongsPlaylist(Users user) {
        Optional<Playlist> likedPlaylistOpt = playlistRepository.findByUserAndName(user, "Liked Songs");
        if (likedPlaylistOpt.isPresent()) {
            return likedPlaylistOpt.get();
        } else {
            Playlist likedPlaylist = new Playlist();
            likedPlaylist.setName("Liked Songs");
            likedPlaylist.setDescription("Плейлист с понравившимися песнями");
            likedPlaylist.setCoverImage("/images/default_playlist.png"); // Дефолтная обложка
            likedPlaylist.setCreatedAt(LocalDateTime.now());
            likedPlaylist.setUser(user);
            likedPlaylist.setSongsCount(0);
            return playlistRepository.save(likedPlaylist);
        }
    }

    // DTO для запроса "лайка"
    public static class FavoriteRequestDTO {
        private boolean favorite;

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }
    }

    // DTO для ответа
    public static class TrackResponseDTO {
        private Long id;
        private String title;
        private String artistName;
        private String duration;
        private boolean isLiked;

        public TrackResponseDTO(Track track) {
            this.id = track.getId();
            this.title = track.getTitle();
            this.artistName = track.getArtist() != null ? track.getArtist().getName() : "Unknown";
            this.duration = track.getDuration();
            // Определяем, находится ли трек в плейлисте "Liked Songs"
            this.isLiked = track.getLikedByUsers() != null && !track.getLikedByUsers().isEmpty();
        }

        // Геттеры и сеттеры
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getArtistName() { return artistName; }
        public void setArtistName(String artistName) { this.artistName = artistName; }

        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }

        public boolean isLiked() { return isLiked; }
        public void setLiked(boolean liked) { isLiked = liked; }
    }
}
