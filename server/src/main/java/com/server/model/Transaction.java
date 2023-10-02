package com.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("transaction")
public class Transaction {
    @Id
    String id;
    Date date;
    String type;
    Double amount;
    String customerId;
    String tellerEmployee;
    String description;

    public Transaction() {
    }

    public Transaction(String id, Date date, String type, Double amount, String customerId, String tellerEmployee, String description) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.customerId = customerId;
        this.tellerEmployee = tellerEmployee;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTellerEmployee() {
        return tellerEmployee;
    }

    public void setTellerEmployee(String tellerEmployee) {
        this.tellerEmployee = tellerEmployee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
