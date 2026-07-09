package vn.com.shop.service;

import vn.com.shop.dto.product.InventoryAdjustmentRequestDTO;
import vn.com.shop.dto.product.InventoryDashboardDTO;
import vn.com.shop.entity.OrderEntity;

public interface IInventoryService {

    void deductStock(OrderEntity order);

    void restoreStock(OrderEntity order);
    void adjustInventory(
            InventoryAdjustmentRequestDTO request
    );
    InventoryDashboardDTO getDashboard();
}
