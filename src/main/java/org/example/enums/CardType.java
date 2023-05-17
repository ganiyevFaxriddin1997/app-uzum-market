package org.example.enums;

public enum CardType {

    HUMO,
    UZCARD,
    VISA;

    public CardType getCardType(String type) {
        for (CardType cardType : values()) {
            if (cardType.name().equals(type))
                return cardType;
        }
        return null;
    }
}
