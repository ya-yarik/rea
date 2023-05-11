package com.example.project.models;

import jakarta.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    private String name;
    private float price;
    private float weight;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "product_id")
    private int productId;

    public Cart(int personId, int productId) {
        this.personId = personId;
        this.productId = productId;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}