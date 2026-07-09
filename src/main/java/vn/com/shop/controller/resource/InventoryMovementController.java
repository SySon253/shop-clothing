package vn.com.shop.controller.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.shop.dto.product.InventoryMovementResponseDTO;
import vn.com.shop.service.IInventoryMovementService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final IInventoryMovementService service;

    @GetMapping("/{variantId}")
    public ResponseEntity<
                List<InventoryMovementResponseDTO>
                > getHistory(

            @PathVariable
            Long variantId){

        return ResponseEntity.ok(

                service.getHistory(
                        variantId
                )

        );

    }

}