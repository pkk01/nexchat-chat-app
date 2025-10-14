package com.chatapp.realtimechatapp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "is_online")
    private boolean isOnline;


}

