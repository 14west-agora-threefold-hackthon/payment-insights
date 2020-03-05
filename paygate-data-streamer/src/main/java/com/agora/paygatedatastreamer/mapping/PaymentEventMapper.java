package com.agora.paygatedatastreamer.mapping;

import com.agora.paygatedatastreamer.entity.PaymentEvent;
import com.agora.paygatedatastreamer.input.PaymentEventInput;
import org.apache.commons.lang3.RandomStringUtils;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PaymentEventMapper {

    private static EasyRandom EASY_RANDOM = new EasyRandom();

    public PaymentEvent mapToPaymentEvent(PaymentEventInput paymentEventInput) {
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setDateAndTime(paymentEventInput.getTimestamp());
        paymentEvent.setOrderId(paymentEventInput.getReference());
        paymentEvent.setPaymentProcessor(paymentEventInput.getPaymentServiceProvider().getName());
        paymentEvent.setPaymentType("card");
        paymentEvent.setTransactionStatus(paymentEventInput.getTransactionStatus());
        paymentEvent.setTransactionType(paymentEventInput.getTransactionType());

        unpackPspResponse(paymentEventInput, paymentEvent);

        decoratePaymentEvent(paymentEvent);

        return paymentEvent;
    }

    private void unpackPspResponse(PaymentEventInput input, PaymentEvent event) {
        String pspResponse = input.getPaymentServiceProvider().getResponse();

        if (pspResponse.length() > 0 && pspResponse.contains("}") && pspResponse.contains("{")) {
            String fieldsString = pspResponse.substring(pspResponse.indexOf('{') + 1, pspResponse.indexOf('}') - 1);
            List<String> fields = Arrays.asList(fieldsString.split(","));

            Map<String, String> fieldValues = new HashMap<>();

            fields.forEach(field ->
                    fieldValues.put(
                            field.split("=")[0].trim().replace("'", ""),
                            field.split("=")[1].trim().replace("'", "")));

            event.setReasonCode(fieldValues.get("statusCode"));
            event.setReasonMessage(fieldValues.get("statusMessage"));
        }
    }

    private void decoratePaymentEvent(PaymentEvent paymentEvent) {
        paymentEvent.setCardExpirationMonth(EASY_RANDOM.nextObject(Date.class).getMonth());
        paymentEvent.setCardExpirationYear(EASY_RANDOM.nextObject(Date.class).getYear());
        paymentEvent.setCustomerEmail("bquigley@tfd.ie");
        paymentEvent.setCustomerFirstName(RandomStringUtils.randomAlphabetic(10));
        paymentEvent.setCustomerLastName(RandomStringUtils.randomAlphabetic(10));
        paymentEvent.setCustomerNumber(RandomStringUtils.randomNumeric(12));
        paymentEvent.setOwningOrg(RandomStringUtils.randomAlphanumeric(5));
        paymentEvent.setPrice(EASY_RANDOM.nextDouble());
    }
}
