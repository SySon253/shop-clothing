package vn.com.shop.dto.chatbot;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

@Getter
@Setter
public class ChatMessageResponseDTO extends BaseResponseDTO {
    private String role;
    private String content;
}