package bg.startit.products.model;

import java.math.BigDecimal;

public class Car extends Product {

    private Long id;
    private String brand_name;
    private String color;
    private BigDecimal price;
    private Long weight;
    private Long length;
    private Long quantity;

    public Car() {
    }

    public Car(String brand_name, String color, BigDecimal price, Long weight, Long length, Long quantity) {
        this.brand_name = brand_name;
        this.color = color;
        this.price = price;
        this.weight = weight;
        this.length = length;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
