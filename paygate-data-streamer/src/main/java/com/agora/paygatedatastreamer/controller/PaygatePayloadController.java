package com.agora.paygatedatastreamer.controller;

import com.agora.paygatedatastreamer.entity.PaymentEvent;
import com.agora.paygatedatastreamer.input.PaymentEventInput;
import com.agora.paygatedatastreamer.mapping.PaymentEventMapper;
import com.agora.paygatedatastreamer.repository.PaymentEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

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

        PaymentEvent paymentEvent = paymentEventMapper.mapToPaymentEvent(paymentEventInput);

        // send post request to email service

        System.out.println(objectMapper.writeValueAsString(paymentEvent));

        paymentEventRepository.save(paymentEvent);
    }
}
