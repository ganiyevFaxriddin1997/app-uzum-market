package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product {
    private int id;
    private String name;
    private double price;
    private int subCategoryId;
    private String description;
    private String color;
    private String size;
    private int ownerId;
    private int amount;
    private String brand;
}
