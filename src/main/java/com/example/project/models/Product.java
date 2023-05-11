package com.example.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "shop_products")
//@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name ="id")
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

    @Column(name = "description", length = 100, columnDefinition = "text")
    private String description;

    @ManyToOne(optional = false)
    private Warehouse warehouse;

    @ManyToOne(optional = false)
    private ProductOwner productOwner;

    @NotEmpty(message = "Поле 'Изготовитель' не может быть пустым")
    @Size (min=1, max = 100, message="Введите наименование изготовителя товара. Число символов от 1 до 100")
    @Column(name = "producer", length = 100, nullable=false, columnDefinition = "text")
    private String producer;

    @ManyToOne(optional = false)
    private Category category;

    private String filePic;

    private LocalDateTime dateTime;



    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<ProductPhotos> imageList = new ArrayList<>();

    //Устанавливается фотография в Листе для данного продукта
    public void addImageToProduct (ProductPhotos image) {
        image.setProduct(this);
        imageList.add(image);
    }

    //Данный метод будет заполнять поля даты и времени пр создании объекта класса
    @PrePersist
    private void init(){
        dateTime = LocalDateTime.now();
    }

    @ManyToMany()
    @JoinTable(name = "cart", joinColumns = @JoinColumn(name = "product_id"),inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<UserModel> personList;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Orders> orderList;

    public Product(String name, float price, String description, Warehouse warehouse, ProductOwner productOwner, Category category, LocalDateTime dateTime, List<ProductPhotos> imageList) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.warehouse = warehouse;
        this.productOwner = productOwner;
        this.category = category;
        this.dateTime = dateTime;
        this.imageList = imageList;
    }

    public Product(String name, float price, float weight, String description, Warehouse warehouse, ProductOwner productOwner, String producer, Category category, List<ProductPhotos> imageList) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.warehouse = warehouse;
        this.productOwner = productOwner;
        this.producer = producer;
        this.category = category;
        this.imageList = imageList;
    }

    public Product(int id, String name, float price, float weight, String description, Warehouse warehouse, ProductOwner productOwner, String producer, Category category, String filePic, LocalDateTime dateTime, List<ProductPhotos> imageList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.warehouse = warehouse;
        this.productOwner = productOwner;
        this.producer = producer;
        this.category = category;
        this.filePic = filePic;
        this.dateTime = dateTime;
        this.imageList = imageList;
    }

    public Product(String name, float price, float weight, String filePic) {
        this.name = name;
        this.price = price;
        this.weight = weight;
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

    public String getFilePic() {
        return filePic;
    }

    public void setFilePic(String filePic) {
        this.filePic = filePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public ProductOwner getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(ProductOwner productOwner) {
        this.productOwner = productOwner;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<ProductPhotos> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductPhotos> imageList) {
        this.imageList = imageList;
    }

    public List<UserModel> getPersonList() {
        return personList;
    }

    public void setPersonList(List<UserModel> personList) {
        this.personList = personList;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", description='" + description + '\'' +
                ", warehouse='" + warehouse + '\'' +
                ", productOwner='" + productOwner + '\'' +
                ", producer='" + producer + '\'' +
                ", category=" + category +
                ", filePic='" + filePic + '\'' +
                ", dateTime=" + dateTime +
                ", imageList=" + imageList +
                '}';
    }
}