package vn.com.shop.controller.page.cms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms")
public class ProductController {
    // url: localhost:8080/cms/product-manager
    @GetMapping("/product-manager")
    public String productManager() {
        return "cms/product-manager";
    }
    // url: localhost:8080/cms/product-orders
    @GetMapping("/product-orders")
    public String productOrders() {
        return "cms/product-orders";
    }
    // url: localhost:8080/cms/product-inventory
    @GetMapping("/product-inventory")
    public String productInventory() {
        return "cms/product-inventory";
    }
}
