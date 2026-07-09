package vn.com.shop.controller.resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestSecurityController {
    @GetMapping("/me")
    public Map<String,Object> getCurrentUser(){


        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();



        Map<String,Object> result =
                new HashMap<>();


        result.put(
                "principal",
                authentication.getPrincipal()
        );


        result.put(
                "principalClass",
                authentication
                        .getPrincipal()
                        .getClass()
                        .getName()
        );


        result.put(
                "name",
                authentication.getName()
        );


        result.put(
                "authorities",
                authentication.getAuthorities()
        );


        return result;

    }
}
