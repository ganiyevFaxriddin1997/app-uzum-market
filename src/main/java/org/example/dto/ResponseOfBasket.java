package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseOfBasket {
    private String name;
    private double price;
    private String description;
    private String color;
    private String size;
    private String brand;
    private int amountProduct;
}
