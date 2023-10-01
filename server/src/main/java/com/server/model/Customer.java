package com.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("customers")
public class Customer {
    @Id
    String id;
    String name;
    String email;
    String phone;
    String address;
    List<String> account_ids;

    public Customer() {
    }

    public Customer(String id, String name, String email, String phone, String address, List<String> account_ids)   {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.account_ids = account_ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getAccount_ids() {
        return account_ids;
    }

    public void setAccount_ids(List<String> account_ids) {
        this.account_ids = account_ids;
    }
}
