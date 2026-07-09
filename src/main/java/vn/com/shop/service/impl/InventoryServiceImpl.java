package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.shop.dto.product.InventoryAdjustmentRequestDTO;
import vn.com.shop.dto.product.InventoryDashboardDTO;
import vn.com.shop.dto.product.InventoryMovementType;
import vn.com.shop.entity.OrderEntity;
import vn.com.shop.entity.OrderItemEntity;
import vn.com.shop.entity.ProductVariantEntity;
import vn.com.shop.repository.ProductVariantRepository;
import vn.com.shop.service.IInventoryMovementService;
import vn.com.shop.service.IInventoryService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements IInventoryService {
    private final ProductVariantRepository variantRepository;
    private final IInventoryMovementService movementService;
    @Override
    public void deductStock(OrderEntity order) {

        for (OrderItemEntity item : order.getItems()) {

            ProductVariantEntity variant =
                    variantRepository
                            .findByIdForUpdate(
                                    item.getProductVariant().getId()
                            )
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Variant Not Found"
                                    )
                            );

            if (variant.getStock() < item.getQuantity()) {

                throw new RuntimeException(
                        "Stock Not Enough"
                );

            }
            int before = variant.getStock();

            int after = before - item.getQuantity();

            variant.setStock(after);

            variant.setSold(
                    variant.getSold()
                            + item.getQuantity()
            );

            movementService.createMovement(

                    variant,

                    InventoryMovementType.SALE,

                    before,

                    -item.getQuantity(),

                    after,

                    "Customer purchased",

                    order.getOrderCode()

            );

        }

    }

    @Override
    public void restoreStock(OrderEntity order) {

        for (OrderItemEntity item : order.getItems()) {
            ProductVariantEntity variant =
                    variantRepository
                            .findByIdForUpdate(
                                    item.getProductVariant().getId()
                            )
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "Variant Not Found"
                                    )
                            );
            int before = variant.getStock();

            int after = before + item.getQuantity();

            variant.setStock(after);

            variant.setSold(
                    variant.getSold()
                            - item.getQuantity()
            );

            movementService.createMovement(

                    variant,

                    InventoryMovementType.CANCEL_ORDER,

                    before,

                    item.getQuantity(),

                    after,

                    "Cancel order",

                    order.getOrderCode()

            );

        }

    }
    @Override
    @Transactional
    public void adjustInventory(
            InventoryAdjustmentRequestDTO request
    ) {

        ProductVariantEntity variant =

                variantRepository
                        .findByIdForUpdate(
                                request.getVariantId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Variant not found"
                                )
                        );

        int before = variant.getStock();

        int after = before;

        switch (request.getMovementType()) {

            case IMPORT, RETURN -> {

                after += request.getQuantity();

            }

            case DAMAGED,
                 LOST -> {

                if (before < request.getQuantity()) {

                    throw new RuntimeException(
                            "Stock not enough"
                    );

                }

                after -= request.getQuantity();

            }

            case ADJUSTMENT -> {

                after += request.getQuantity();

                if (after < 0) {

                    throw new RuntimeException(
                            "Stock cannot be negative"
                    );

                }

            }

            default ->

                    throw new RuntimeException(
                            "Movement type is not allowed"
                    );

        }

        variant.setStock(after);

        variantRepository.save(variant);

        movementService.createMovement(

                variant,

                request.getMovementType(),

                before,

                after - before,

                after,

                request.getReason(),

                request.getReferenceCode()

        );

    }
    @Override
    @Transactional(readOnly = true)
    public InventoryDashboardDTO getDashboard() {

        List<ProductVariantEntity> variants =
                variantRepository.findByDeletedFalse();

        InventoryDashboardDTO dto =
                new InventoryDashboardDTO();

        dto.setTotalVariants(
                (long) variants.size()
        );

        dto.setLowStock(

                variants.stream()

                        .filter(v -> {

                            int reserved =

                                    v.getReservedStock() == null

                                            ? 0

                                            : v.getReservedStock();

                            int available =
                                    v.getStock() - reserved;

                            return available > 0

                                    && available <= 20;

                        })

                        .count()

        );

        dto.setOutOfStock(

                variants.stream()

                        .filter(v -> {

                            int reserved =

                                    v.getReservedStock() == null

                                            ? 0

                                            : v.getReservedStock();

                            return v.getStock() - reserved <= 0;

                        })

                        .count()

        );

        BigDecimal value = BigDecimal.ZERO;

        for (ProductVariantEntity variant : variants) {

            BigDecimal price =
                    variant.getDiscountPrice() != null
                            ? variant.getDiscountPrice()
                            : variant.getPrice();

            int available =
                    variant.getStock()
                            - (variant.getReservedStock() == null ? 0 : variant.getReservedStock());

            value = value.add(
                    price.multiply(
                            BigDecimal.valueOf(available)
                    )
            );
        }

        dto.setInventoryValue(value);

        return dto;

    }
}
