package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "added_date")
    private Date addedDate;

    private String description;
    private String duration;
    private String filePath;
    private String imagePath;
    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    // Связь Many-to-Many с пользователями для лайков
    @ManyToMany(mappedBy = "likedTracks")
    @JsonBackReference
    private Set<Users> likedByUsers = new HashSet<>();

    // Конструкторы
    public Track() {}

    public Track(Date addedDate, String description, String duration, String filePath, String imagePath, String title, Artist artist) {
        this.addedDate = addedDate;
        this.description = description;
        this.duration = duration;
        this.filePath = filePath;
        this.imagePath = imagePath;
        this.title = title;
        this.artist = artist;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Set<Users> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<Users> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }
}
