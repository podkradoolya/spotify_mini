package com.example.demo.repository;

import com.example.demo.model.Playlist;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUser(Users user);
    Optional<Playlist> findByUserAndName(Users user, String name);
}
