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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private int userId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private boolean deleted;
    private String code;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"), // Cấu hình để thuộc tính user_id trong bảng phụ user_roles sẽ là khóa phụ tham chiếu tới cột id trong bảng users
            inverseJoinColumns = @JoinColumn(name = "role_id")// Cấu hình để thuộc tính role_id trong bảng phụ user_roles sẽ là khóa phụ tham chiếu tới cột trong bảng roles
    )
    private Set<RoleEntity> roles = new HashSet<>();
}
