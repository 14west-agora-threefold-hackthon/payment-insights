package com.agora.paymenteventgenerator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class PaymentEventGeneratorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PaymentEventGeneratorApplication.class, args);
	}

	public void run(String... args) throws IOException, URISyntaxException {
		URL resourcePath = getClass().getResource("input.json");

		List<String> paygateEvents = Files.readAllLines(Paths.get(resourcePath.toURI()));

		for(String event: paygateEvents) {
			System.out.println(event);
		}
	}
}
