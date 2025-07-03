package com.zamy.santriapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zamy.santriapp.entity.Admin;
import com.zamy.santriapp.repository.AdminRepository;

@SpringBootApplication
public class SantriappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SantriappApplication.class, args);
	}

	@Bean
CommandLineRunner init(AdminRepository adminRepo, BCryptPasswordEncoder encoder) {
    return args -> {
        if (adminRepo.findByUsername("admin") == null) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123")); // Ubah sesuai kebutuhan
            adminRepo.save(admin);
            System.out.println("Admin default dibuat: admin / admin123");
        }
    };
}


}
