package org.example.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private int id;
    private String name;
    private double price;
    private int subcategoryId;
    private String description;
    private String color;
    private String size;
    private int ownerId;
    private int amount;
}
