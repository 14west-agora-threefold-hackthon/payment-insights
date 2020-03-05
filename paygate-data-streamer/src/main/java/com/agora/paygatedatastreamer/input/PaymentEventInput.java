package com.agora.paygatedatastreamer.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentEventInput {

    @JsonProperty("modoAction")
    private String transactionType;

    @JsonProperty("status")
    private String transactionStatus;

    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("psp")
    private PaymentServiceProvider paymentServiceProvider;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaymentServiceProvider {
        @JsonProperty("name")
        private String name;

        @JsonProperty("resp")
        private String response;

        public String getName() {
            return name;
        }

        public String getResponse() {
            return response;
        }
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getReference() {
        return reference;
    }

    public PaymentServiceProvider getPaymentServiceProvider() {
        return paymentServiceProvider;
    }
}
