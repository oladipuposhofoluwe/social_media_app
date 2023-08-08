package com.socialmediaapp.repository.user;

import com.socialmediaapp.dto.user.UserDto;
import com.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
    Optional<User> findByEmailOrUserName(String email, String userName);
    Optional<User> findUserById(long userId);

    Optional<User> findUserByUserName(String userName);
}
