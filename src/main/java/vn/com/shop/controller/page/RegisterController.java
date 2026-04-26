package vn.com.shop.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    @GetMapping(value = "/register")
    public String RegisterController(){
        return "guest/register";
    }
}
