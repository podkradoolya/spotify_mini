package com.example.demo.repository;

import com.example.demo.model.Users;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    // Найти пользователя по ID
    Optional<Users> findById(Long id);

    // Найти пользователя по email (опционально)
    Optional<Users> findByEmail(String email);

    // Найти пользователя по nickname (опционально)
    Optional<Users> findByNickname(String nickname);

}
