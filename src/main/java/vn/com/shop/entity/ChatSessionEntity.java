package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "chat_sessions")
@Getter
@Setter
public class ChatSessionEntity extends BaseEntity {
    @ManyToOne
    private UserEntity user;

    private String title;

    @OneToMany(mappedBy = "session")
    private Set<ChatMessageEntity> messages;
}
