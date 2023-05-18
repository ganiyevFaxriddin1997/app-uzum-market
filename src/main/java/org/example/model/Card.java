package org.example.model;


import lombok.*;
import org.example.enums.CardType;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Card {

    private int id;
    private String serialNumber;
    private CardType cardType;
    private Timestamp date;
    private int ownerId;
    private int password;
    private double balance;
}
