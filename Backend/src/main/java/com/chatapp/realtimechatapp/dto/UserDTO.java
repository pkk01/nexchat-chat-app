package com.chatapp.realtimechatapp.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDTO {

    @Getter
    @Setter
    private Long id;

    @Setter
    @Getter
    @Column(unique = true, nullable = false)
    private String username;


    @Setter
    @Getter
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "is_online")
    private boolean isOnline;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
