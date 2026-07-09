package vn.com.shop.mapper;

import org.springframework.stereotype.Component;
import vn.com.shop.dto.product.ProductVariantResponseDTO;
import vn.com.shop.entity.ProductVariantEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class ProductVariantMapper {
    public ProductVariantResponseDTO entityToDto(ProductVariantEntity productVariantEntity) {
        if(productVariantEntity == null) {return null;}
        ProductVariantResponseDTO productVariantResponseDTO = new ProductVariantResponseDTO();
        productVariantResponseDTO.setId(productVariantEntity.getId());
        productVariantResponseDTO.setSku(productVariantEntity.getSku());
        productVariantResponseDTO.setPrice(productVariantEntity.getPrice());
        productVariantResponseDTO.setDiscountPrice(productVariantEntity.getDiscountPrice());
        productVariantResponseDTO.setStock(productVariantEntity.getStock());
        productVariantResponseDTO.setReservedStock(productVariantEntity.getReservedStock());
        productVariantResponseDTO.setSize(productVariantEntity.getSize());
        productVariantResponseDTO.setColor(productVariantEntity.getColor());
        productVariantResponseDTO.setSold(productVariantEntity.getSold());
        productVariantResponseDTO.setCreatedBy(productVariantEntity.getCreatedBy());
        productVariantResponseDTO.setCreatedDate(productVariantEntity.getCreatedDate());
        productVariantResponseDTO.setLastModifiedBy(productVariantEntity.getLastModifiedBy());
        productVariantResponseDTO.setLastModifiedDate(productVariantEntity.getLastModifiedDate());
        productVariantResponseDTO.setProductName(productVariantEntity.getProduct().getName());

//        int available =
//
//                productVariantEntity.getStock()
//
//                        -
//
//                        productVariantEntity.getReservedStock();
        int reserved =

                productVariantEntity.getReservedStock() == null

                        ? 0

                        : productVariantEntity.getReservedStock();

        int available =
                productVariantEntity.getStock()
                        - reserved;

        productVariantResponseDTO.setAvailableStock(
                available
        );

        BigDecimal price =

                productVariantEntity.getDiscountPrice() != null

                        ? productVariantEntity.getDiscountPrice()

                        : productVariantEntity.getPrice();

        productVariantResponseDTO.setInventoryValue(

                available *

                        price.doubleValue()

        );

        productVariantResponseDTO.setProductName(

                productVariantEntity
                        .getProduct()
                        .getName()

        );

        if(available<=0){

            productVariantResponseDTO.setStockStatus(
                    "OUT_OF_STOCK"
            );

        }
        else if(available<=5){

            productVariantResponseDTO.setStockStatus(
                    "CRITICAL"
            );

        }
        else if(available<=20){

            productVariantResponseDTO.setStockStatus(
                    "LOW_STOCK"
            );

        }
        else{

            productVariantResponseDTO.setStockStatus(
                    "IN_STOCK"
            );

        }

        //productVariantResponseDTO.setStockStatus(status);
        return productVariantResponseDTO;
    }
    public List<ProductVariantResponseDTO> entityToDto(Set<ProductVariantEntity> productVariantEntityList) {
        if(productVariantEntityList == null) {return null;}
        return productVariantEntityList.stream().map(this::entityToDto).toList();
    }
}
