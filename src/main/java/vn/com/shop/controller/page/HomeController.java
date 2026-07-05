package vn.com.shop.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
@Controller
public class HomeController {
    @GetMapping(value = "/home")
    public String home() {
        return "guest/index";
    }

    @GetMapping(value = "/shop")
    public String shop() { return "guest/shop"; }

    @GetMapping("/product/{id}")
    public String detail(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute("productId", id);

        return "guest/detail";

    }

    @GetMapping(value = "/cart")
    public String cart() { return "guest/cart"; }

//    @GetMapping(value = "/checkout")
//    public String checkout() { return "guest/checkout"; }
}
