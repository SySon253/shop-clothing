package vn.com.shop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponsePage<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;

    //    private List<ClaimDTO> content;
    private List<T> content;
    public ResponsePage() {
    }
    public ResponsePage(
            Integer pageNumber,
            Integer pageSize,
            Long totalElements,
            Integer totalPages,
            List<T> content
    ) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }
}
