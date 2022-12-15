package io.github.CarolinaCedro.POC01;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class Poc01AddressApplication {

    public static void main(String[] args) {
        SpringApplication.run(Poc01AddressApplication.class, args);
    }

}
