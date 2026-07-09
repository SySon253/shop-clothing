package vn.com.shop.controller.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.shop.dto.product.InventoryAdjustmentRequestDTO;
import vn.com.shop.dto.product.InventoryDashboardDTO;
import vn.com.shop.service.IInventoryService;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final IInventoryService inventoryService;

    @PostMapping("/adjust")
    public ResponseEntity<Void> adjustInventory(

            @Valid

            @RequestBody

            InventoryAdjustmentRequestDTO request

    ){

        inventoryService.adjustInventory(
                request
        );

        return ResponseEntity.ok().build();

    }
    @GetMapping("/dashboard")
    public ResponseEntity<InventoryDashboardDTO>
    dashboard(){

        return ResponseEntity.ok(

                inventoryService.getDashboard()

        );

    }

}