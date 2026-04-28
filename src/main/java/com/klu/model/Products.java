package com.klu.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("name")
    private String productname;
    private String category;
    private String artisan;
    private Double price;
    private Integer quantity;
    private Integer stock;
    @Column(length = 1000000)
    private String image;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String culturalNotes;
    private Double rating;

    public Products(Long id, String productname, Double price, Integer quantity) {
        super();
        this.id = id;
        this.productname = productname;
        this.price = price;
        this.quantity = quantity;
    }

    // Default constructor (Required)
    public Products() {}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getName() {
        return productname;
    }

    public void setName(String name) {
        this.productname = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArtisan() {
        return artisan;
    }

    public void setArtisan(String artisan) {
        this.artisan = artisan;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCulturalNotes() {
        return culturalNotes;
    }

    public void setCulturalNotes(String culturalNotes) {
        this.culturalNotes = culturalNotes;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
