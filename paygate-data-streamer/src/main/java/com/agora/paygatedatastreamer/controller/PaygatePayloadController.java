package com.agora.paygatedatastreamer.controller;

import com.agora.paygatedatastreamer.entity.PaymentEvent;
import com.agora.paygatedatastreamer.input.PaymentEventInput;
import com.agora.paygatedatastreamer.mapping.PaymentEventMapper;
import com.agora.paygatedatastreamer.repository.PaymentEventRepository;
import com.agora.paygatedatastreamer.util.RequestResponseLoggingInterceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@RestController
public class PaygatePayloadController {

    private PaymentEventRepository paymentEventRepository;

    private PaymentEventMapper paymentEventMapper;

    private ObjectMapper objectMapper;

    public PaygatePayloadController(PaymentEventRepository paymentEventRepository,
                                    PaymentEventMapper paymentEventMapper,
                                    ObjectMapper objectMapper) {
        this.paymentEventRepository = paymentEventRepository;
        this.paymentEventMapper = paymentEventMapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "ingest_payment_event", consumes = "application/json")
    public void ingestPaymentEvent(@RequestBody PaymentEventInput paymentEventInput)
            throws JsonProcessingException {

        try {
            PaymentEvent paymentEvent = paymentEventMapper.mapToPaymentEvent(paymentEventInput);

            System.out.println("Received: " + objectMapper.writeValueAsString(paymentEvent));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PaymentEvent> request = new HttpEntity<>(paymentEvent, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

            restTemplate.postForObject(URI.create(
                    "https://push-service-payment-insights.apps.threefold.x1l7.p1.openshiftapps.com/v1/emails"),
                    request, String.class);

            paymentEventRepository.save(paymentEvent);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getStackTrace());
        }
    }
}
