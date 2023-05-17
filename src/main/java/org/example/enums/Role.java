package org.example.enums;

public enum Role {
    OWNER,
    ADMIN,
    USER,
    PRODUCT_OWNER;

    public Role getRole(String roleName) {
        for (Role role : values()) {
            if (role.name().equals(roleName))
                return role;
        }
        return null;
    }
}
