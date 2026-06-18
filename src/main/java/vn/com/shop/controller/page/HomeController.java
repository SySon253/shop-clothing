package vn.com.shop.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/home")
    public String home() {
        return "guest/index";
    }

    @GetMapping(value = "/shop")
    public String shop() { return "guest/shop"; }

    @GetMapping(value = "/detail")
    public String detail() { return "guest/detail"; }

    @GetMapping(value = "/cart")
    public String cart() { return "guest/cart"; }

//    @GetMapping(value = "/checkout")
//    public String checkout() { return "guest/checkout"; }
}
