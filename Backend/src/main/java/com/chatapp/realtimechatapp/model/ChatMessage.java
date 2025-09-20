package com.chatapp.realtimechatapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String sender;
    private String recipient;

    private String color;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    public enum MessageType {
        CHAT, PRIVATE_MESSAGE, JOIN, LEAVE, TYPING
    }

}
