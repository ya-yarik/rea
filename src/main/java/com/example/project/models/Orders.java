package com.example.project.models;

import com.example.project.enumm.Statuses;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserModel users;

    private int count;
    private float price;
    private LocalDateTime dateTime;

    private Statuses statuses;

    @PrePersist
    private void init(){
        dateTime = LocalDateTime.now();
    }

    public Orders(String number, Product product, UserModel users, int count, float price, Statuses statuses) {
        this.number = number;
        this.product = product;
        this.users = users;
        this.count = count;
        this.price = price;
        this.statuses = statuses;
    }

    public Orders() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserModel getUsers() {
        return users;
    }

    public void setUsers(UserModel users) {
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Statuses getStatuses() {
        return statuses;
    }

    public void setStatuses(Statuses statuses) {
        this.statuses = statuses;
    }
}
