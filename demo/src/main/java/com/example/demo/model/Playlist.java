package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String coverImage;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "songs_count")
    private int songsCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Скрыть пользователя при сериализации
    private Users user;

    @ManyToMany
    @JoinTable(
            name = "playlist_track",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    @JsonManagedReference // Для управления ссылками с Jackson
    private Set<Track> tracks = new HashSet<>();

    // getters / setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getCoverImage() {
        return coverImage;
    }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getSongsCount() {
        return songsCount;
    }
    public void setSongsCount(int songsCount) { this.songsCount = songsCount; }

    public Users getUser() {
        return user;
    }
    public void setUser(Users user) { this.user = user; }

    public Set<Track> getTracks() {
        return tracks;
    }
    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
        this.songsCount = this.tracks.size();
    }

    public void removeTrack(Track track) {
        this.tracks.remove(track);
        this.songsCount = this.tracks.size();
    }
}
