package org.example.model;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

    private int id;
    private int cardId;
    private int orderId;
    private double price;
    private Timestamp createdDate;
}
