package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
public class Payment {
    private int id;
    private int cardId;
    private List<Order> orders;
    private double price;
    private Timestamp createdDate;
}




