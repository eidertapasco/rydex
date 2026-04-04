package com.bikeshop.rydex;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class RydexApplication {

	public static void main(String[] args) {
		SpringApplication.run(RydexApplication.class, args);
	}

    // Este metodo es para forzar la zona horaria
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
    }
}