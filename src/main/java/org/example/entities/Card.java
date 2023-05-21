package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.CardType;

import java.sql.Timestamp;

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
