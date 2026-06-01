package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "chat_messages")
@Getter
@Setter
public class ChatMessageEntity extends BaseEntity {
    @ManyToOne
    private ChatSessionEntity session;

    @Enumerated(EnumType.STRING)
    private MessageRole role;

    @Lob
    private String content;
}
