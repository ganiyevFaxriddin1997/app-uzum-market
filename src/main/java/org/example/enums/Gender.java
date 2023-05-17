package org.example.enums;

public enum Gender {
    MALE,
    FEMALE;

    public Gender getGender(String genderName) {
        Gender[] values = values();
        for (Gender gender : values) {
            if (gender.name().equals(genderName))
                return gender;
        }
        return null;
    }
}
