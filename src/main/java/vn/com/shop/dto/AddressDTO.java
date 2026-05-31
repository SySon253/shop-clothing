package vn.com.shop.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;

    private String unitNumber;

    private String streetNumber;

    private String addressLine1;

    private String city;

    private String postalCode;
}
