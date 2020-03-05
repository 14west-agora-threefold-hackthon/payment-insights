package com.agora.paygatedatastreamer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "payment_event")
public class PaymentEvent {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "customer_email")
    private String customerEmail;

    // psp.name
    @Column(name = "payment_procesor")
    private String paymentProcessor;

    @Column(name = "transaction_status")
    private String transactionStatus;

    //psp.resp.statusCode
    @Column(name = "reason_code")
    private String reasonCode;

    //psp.resp.statusMessage
    @Column(name = "reason_message")
    private String reasonMessage;

    @Column(name = "price")
    private Double price;

    @Column(name = "date_and_time")
    private Timestamp dateAndTime;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "owning_org")
    private String owningOrg;

    // hardcode as card
    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "card_expiration_month")
    private int cardExpirationMonth;

    @Column(name = "card_expiration_year")
    private int cardExpirationYear;

    public int getId() {
        return id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(String paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonMessage() {
        return reasonMessage;
    }

    public void setReasonMessage(String reasonMessage) {
        this.reasonMessage = reasonMessage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOwningOrg() {
        return owningOrg;
    }

    public void setOwningOrg(String owningOrg) {
        this.owningOrg = owningOrg;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    public void setCardExpirationMonth(int cardExpirationMonth) {
        this.cardExpirationMonth = cardExpirationMonth;
    }

    public int getCardExpirationYear() {
        return cardExpirationYear;
    }

    public void setCardExpirationYear(int cardExpirationYear) {
        this.cardExpirationYear = cardExpirationYear;
    }
}
