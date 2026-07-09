package vn.com.shop.dto.category;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryUpdateDTO {
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private String name;
    private String slug;
    private Long parentId;
}
