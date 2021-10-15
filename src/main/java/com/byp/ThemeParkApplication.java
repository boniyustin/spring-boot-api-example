package com.byp;

import com.byp.common.JWTAuthorizationFilter;
import com.byp.inventory.entity.Product;
import com.byp.inventory.repository.ProductRepository;
import com.byp.user.entity.UserProfile;
import com.byp.user.repository.UserProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Date;

@SpringBootApplication
public class ThemeParkApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ThemeParkApplication.class);
    }

    @EnableWebSecurity
    @Configuration
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
              .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
              .authorizeRequests()
              .antMatchers(HttpMethod.POST, "/user").permitAll()
              .anyRequest().authenticated();
        }
    }


    @Bean
    public CommandLineRunner sampleData(ProductRepository repository) {
        return (args) -> {
            repository.save(new Product("iPhone 12 PRO", "Specifications is good", 100, 12500000, "IDR"));
            repository.save(new Product("Samsung PRO", "Best Spec.", 100, 2100000, "IDR"));
            repository.save(new Product("Screen Guard 5.5 inch", "Good Accessoris", 100, 50000, "IDR"));
        };
    }

    @Bean
    public CommandLineRunner sampleUser(UserProfileRepository repository) {
        return (args) -> {
            repository.save(new UserProfile("byp", "test-password",
              "Boni Yustin Prabowo", "boniyustin@gmail.com",
              "085211223344", "Depok", new Date(1991, 1, 1), 0));
        };
    }


}
