package com.chatapp.realtimechatapp.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;
    private UserDTO userDTO;
}
