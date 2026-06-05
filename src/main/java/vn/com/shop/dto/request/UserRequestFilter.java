package vn.com.shop.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequestFilter {
    private String keyword;

    private String roleName;

    private Boolean enabled;

    private Boolean deleted;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;
}
