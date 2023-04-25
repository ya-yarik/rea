package com.example.project.models;

import com.example.project.enumm.Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity(name = "shop_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Поле 'Наименование' не может быть пустым")
    @Size (min=1, max = 100, message="Введите наименование товара. Число символов от 1 до 100")
    @Column(name = "name", length = 100, nullable=false, columnDefinition = "text")
    private String name;

    @NotNull(message = "Поле 'Цена' не может быть пустым")
    @Min(value=0, message="Цена всегда имеет положительное значение")
    @Column(name = "price", length = 100, nullable=false, columnDefinition = "FLOAT(30)")
    private float price;

    @NotNull(message = "Поле 'Вес' не может быть пустым")
    @Min(value=0, message="Вес товара не может быть меньше нуля")
    @Column(name = "weight", length = 100, nullable=false, columnDefinition = "FLOAT(30)")
    private float weight;
    private Provider provider;

    private String filePic;

    public Product(int id, String name, float price, float weight, Provider provider, String filePic) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.provider = provider;
        this.filePic = filePic;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
//        this.id = ++id;
        this.id = id;
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
        float a = Math.round(price * 100);
        this.price = a/100;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        float b = Math.round(weight * 100);
        this.weight = b/100;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getFilePic() {
        return filePic;
    }

    public void setFilePic(String filePic) {
        this.filePic = filePic;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", provider=" + provider +
                '}';
    }
}
