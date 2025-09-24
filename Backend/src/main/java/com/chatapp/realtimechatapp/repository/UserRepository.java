package com.chatapp.realtimechatapp.repository;

import com.chatapp.realtimechatapp.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByUsername(String username);

    public Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isOnline = :isOnline WHERE u.username = :username")
    public void updateUserOnlineStatus(@Param("username") String username, @Param("isOnline") boolean isonline);


}
