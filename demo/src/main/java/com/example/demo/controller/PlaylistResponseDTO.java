package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.model.Track;
import java.util.Set;
import java.util.stream.Collectors;

public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private String coverImage;
    private String description;
    private int songsCount;

    // Дополнительные поля, если необходимо

    public PlaylistResponseDTO(Playlist playlist) {
        this.id = playlist.getId();
        this.name = playlist.getName();
        this.coverImage = playlist.getCoverImage();
        this.description = playlist.getDescription();
        this.songsCount = playlist.getSongsCount();
    }

    // getters и setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getSongsCount() { return songsCount; }
    public void setSongsCount(int songsCount) { this.songsCount = songsCount; }
}
