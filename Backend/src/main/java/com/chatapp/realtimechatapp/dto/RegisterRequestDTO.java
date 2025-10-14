package com.chatapp.realtimechatapp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;

}
