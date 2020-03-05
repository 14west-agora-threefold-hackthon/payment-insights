package com.agora.paygatedatastreamer;

import com.agora.paygatedatastreamer.entity.PaymentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaygateDataStreamerApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(PaygateDataStreamerApplication.class, args);

		ObjectMapper objectMapper = new ObjectMapper();

		EasyRandom generator = new EasyRandom();
		PaymentEvent paymentEvent = generator.nextObject(PaymentEvent.class);

		System.out.println(objectMapper.writeValueAsString(paymentEvent));
	}

}
