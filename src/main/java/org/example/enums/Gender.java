package org.example.enums;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender getGender(String gender) {
        for (Gender value : values()) {
            if (value.name().equals(gender)) {
                return value;
            }
        }
        return null;
    }
}
