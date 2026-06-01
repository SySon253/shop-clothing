package vn.com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

// Đánh dấu class này là một entity ứng với một table trong db
@Entity
@Table(name = "users") // chỉ định UserEntity ứng với table users trong db
@Getter
@Setter
public class UserEntity extends BaseEntity{
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"), // Cấu hình để thuộc tính user_id trong bảng phụ user_roles sẽ là khóa phụ tham chiếu tới cột id trong bảng users
            inverseJoinColumns = @JoinColumn(name = "role_id")// Cấu hình để thuộc tính role_id trong bảng phụ user_roles sẽ là khóa phụ tham chiếu tới cột trong bảng roles
    )
    private Set<RoleEntity> roles = new HashSet<>();
}
