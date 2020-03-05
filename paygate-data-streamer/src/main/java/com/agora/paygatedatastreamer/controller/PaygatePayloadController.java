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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class PaygatePayloadController {

    private PaymentEventRepository paymentEventRepository;

    private PaymentEventMapper paymentEventMapper;

    private ObjectMapper objectMapper;

    private RestTemplate restTemplate = new RestTemplate();

    public PaygatePayloadController(PaymentEventRepository paymentEventRepository,
                                    PaymentEventMapper paymentEventMapper,
                                    ObjectMapper objectMapper) {
        this.paymentEventRepository = paymentEventRepository;
        this.paymentEventMapper = paymentEventMapper;
        this.objectMapper = objectMapper;

        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
    }

    @PostMapping(path = "ingest_payment_event", consumes = "application/json")
    public void ingestPaymentEvent(@RequestBody PaymentEventInput paymentEventInput)
            throws JsonProcessingException {

        try {
            PaymentEvent paymentEvent = paymentEventMapper.mapToPaymentEvent(paymentEventInput);

            System.out.println("Received: " + objectMapper.writeValueAsString(paymentEvent));

            List<String> alertReasons = new ArrayList<>();
            alertReasons.add("Postal code is missing");
            alertReasons.add("stolen card");
            alertReasons.add("lost card");
            alertReasons.add("Expired card");

            if (shouldEmailBeSent(paymentEvent, alertReasons)) {
                overwriteReasonMessage(paymentEvent, alertReasons);
                triggerEmailForEvent(paymentEvent);
            }

            paymentEventRepository.save(paymentEvent);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getStackTrace());
        }
    }

    private boolean shouldEmailBeSent(PaymentEvent paymentEvent, List<String> alertReasons) {
        return alertReasons.stream().anyMatch(reason -> paymentEvent.getReasonMessage().contains(reason));
    }

    private void overwriteReasonMessage(PaymentEvent paymentEvent, List<String> alertReasons) {
        for (String alertReason : alertReasons) {
            if (paymentEvent.getReasonMessage().contains(alertReason)) {
                paymentEvent.setReasonMessage(alertReason);

                return;
            }
        }
    }

    private void triggerEmailForEvent(PaymentEvent paymentEvent) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentEvent> request = new HttpEntity<>(paymentEvent, headers);

        restTemplate.postForObject(URI.create(
                    "https://push-service-payment-insights.apps.threefold.x1l7.p1.openshiftapps.com/v1/emails"),
                    request, String.class);

//        restTemplate.postForObject(URI.create(
//                "https://3a8ac92d.ngrok.io/v1/emails"),
//                request, String.class);
    }
}
