package vn.com.shop.dto.chatbot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDTO {
    private Long sessionId;
    private String content;
}