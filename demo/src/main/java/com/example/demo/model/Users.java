package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")  // Используйте "users" вместо "user"
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;
    private String password;

    @Column(name = "registration_date")
    private Date registrationDate;

    // Связь с плейлистами
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Playlist> playlists = new HashSet<>();

    // Связь Many-to-Many для лайкнутых треков (опционально)
    @ManyToMany
    @JoinTable(
            name = "user_liked_tracks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<Track> likedTracks = new HashSet<>();
    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Set<Track> getLikedTracks() {
        return likedTracks;
    }

    public void setLikedTracks(Set<Track> likedTracks) {
        this.likedTracks = likedTracks;
    }

    // Дополнительные методы для управления плейлистами и лайками
    public void likeTrack(Track track) {
        this.likedTracks.add(track);
        track.getLikedByUsers().add(this);
    }

    public void unlikeTrack(Track track) {
        this.likedTracks.remove(track);
        track.getLikedByUsers().remove(this);
    }
}
