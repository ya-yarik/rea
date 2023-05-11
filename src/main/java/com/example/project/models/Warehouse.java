package com.example.project.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String mainPerson;
    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    private List<Product> product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMainPerson() {
        return mainPerson;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMainPerson(String mainPerson) {
        this.mainPerson = mainPerson;
    }
}
