package com.byp;

import com.byp.inventory.entity.Product;
import com.byp.inventory.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThemeParkApplication  {
    public static void main(String[] args) {
        SpringApplication.run(ThemeParkApplication.class);
    }

    @Bean
    public CommandLineRunner sampleData(ProductRepository repository) {
        return (args) -> {
            repository.save(new Product("iPhone 12 PRO", "Specifications is good", 100, 12500000, "IDR"));
            repository.save(new Product("Samsung PRO", "Best Spec.", 100, 2100000, "IDR"));
            repository.save(new Product("Screen Guard 5.5 inch", "Good Accessoris", 100, 50000, "IDR"));
        };
    }
}
