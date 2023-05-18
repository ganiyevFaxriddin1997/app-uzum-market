package org.example.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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
