package vn.com.shop.dto.response;

import lombok.Data;

@Data
public class ResponsePage<T> {
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;

    //    private List<ClaimDTO> content;
    private T content;
}
