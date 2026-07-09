package vn.com.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing // tự động điền ngày tháng
@EnableScheduling
public class ShopClothingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopClothingApplication.class, args);
	}

//	@Bean
//	CommandLineRunner printPassword(PasswordEncoder passwordEncoder) {
//		return args -> {
//			System.out.println("BCrypt password for 123456:");
//			System.out.println(passwordEncoder.encode("123456"));
//		};
//	}
}
