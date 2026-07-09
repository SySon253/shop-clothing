package vn.com.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.shop.dto.product.InventoryMovementResponseDTO;
import vn.com.shop.dto.product.InventoryMovementType;
import vn.com.shop.entity.InventoryMovementEntity;
import vn.com.shop.entity.ProductVariantEntity;
import vn.com.shop.mapper.InventoryMovementMapper;
import vn.com.shop.repository.InventoryMovementRepository;
import vn.com.shop.service.IInventoryMovementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements IInventoryMovementService {
    private final InventoryMovementRepository repository;
    private final InventoryMovementMapper mapper;
    @Override
    public void createMovement(

            ProductVariantEntity variant,

            InventoryMovementType type,

            Integer before,

            Integer change,

            Integer after,

            String reason,

            String reference){

        InventoryMovementEntity movement =
                new InventoryMovementEntity();

        movement.setVariant(variant);

        movement.setMovementType(type);

        movement.setQuantityBefore(before);

        movement.setQuantityChange(change);

        movement.setQuantityAfter(after);

        movement.setReason(reason);

        movement.setReferenceCode(reference);

        repository.save(movement);

    }
    @Override
    public List<InventoryMovementResponseDTO>
    getHistory(Long variantId){

        return repository

                .findByVariantIdOrderByCreatedDateDesc(
                        variantId
                )

                .stream()

                .map(
                        mapper::entityToDto
                )

                .toList();

    }
}
