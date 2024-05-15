package com.swyp.kiwoyu.user.repository;
import com.swyp.kiwoyu.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

}