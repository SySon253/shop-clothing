package vn.com.shop.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserPageController {

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/products")
    public String products() {
        return "user/products";
    }

    @GetMapping("/promotions")
    public String promotions() {
        return "user/promotions";
    }

    @GetMapping("/cart")
    public String cart() {
        return "user/cart";
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "user/wishlist";
    }

    @GetMapping("/account")
    public String account() {
        return "user/account";
    }

    @GetMapping("/orders")
    public String orders() {
        return "user/orders";
    }
}
