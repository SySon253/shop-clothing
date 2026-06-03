package vn.com.shop.dto.chatbot;

import lombok.Getter;
import lombok.Setter;
import vn.com.shop.dto.BaseResponseDTO;

import java.util.List;

@Getter
@Setter
public class ChatSessionResponseDTO extends BaseResponseDTO {
    private String title;
    private List<ChatMessageResponseDTO> messages;
}