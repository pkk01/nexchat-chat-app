package com.chatapp.realtimechatapp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
@Data
public class LoginResponseDTO {
    private String token;
    private UserDTO userDTO;

}
