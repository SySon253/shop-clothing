package vn.com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // tự động điền ngày tháng
public class ShopClothingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopClothingApplication.class, args);
	}

}
