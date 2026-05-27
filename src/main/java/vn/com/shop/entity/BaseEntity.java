package vn.com.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // Tự động điền ngày giờ nếu bật @EnableJpaAuditing
@Data
public abstract class BaseEntity {
    @CreatedBy
    @Column(name = "Created_by", updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(name = "Created_date", updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "Last_Modified_By")
    protected String lastModifiedBy;
}
