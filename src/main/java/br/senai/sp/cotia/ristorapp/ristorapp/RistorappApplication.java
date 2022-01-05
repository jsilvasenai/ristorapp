package br.senai.sp.cotia.ristorapp.ristorapp;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RistorappApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RistorappApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "80"));
		app.run(args);
	}

}
