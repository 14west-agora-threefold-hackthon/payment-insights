package com.agora.paymenteventgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
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

	public void run(String... args) throws IOException, URISyntaxException, InterruptedException {
		while(true) {
			processAllEvents();
			Thread.sleep(2000);
		}
	}

	private void processAllEvents() throws IOException {
		Files.walk(Paths.get("src/main/resources"))
				.filter( path -> Files.isRegularFile(path))
				.filter( path -> path.getFileName().toString().startsWith("modo-audit"))
				.forEach( filePath -> {

					List<String> paygateEvents = null;
					try {
						paygateEvents = Files.readAllLines(filePath);
					} catch (IOException e) {
						e.printStackTrace();
					}

					assert paygateEvents != null;
					for(String event: paygateEvents) {
						processPaygateEvent(event);
					}
				});
	}

	private void processPaygateEvent(String event) {
		System.out.println(event);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(event, headers);

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.postForObject(URI.create("http://localhost:8080/ingest_payment_event"),
				request, String.class);
	}
}
