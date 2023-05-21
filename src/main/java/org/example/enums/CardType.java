package org.example.enums;

public enum CardType {
    HUMO,
    VISA,
    UZCARD;

    public static CardType getCardType(String card) {
        for (CardType value : values()) {
            if (value.name().equals(card)) {
                return value;
            }
        }
        return null;
    }
}
