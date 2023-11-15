package com.example.serviceprovider.model.enumeration;

public enum Role {
    ROLE_ADMIN("admin"),
    ROLE_EXPERT("expert"),
    ROLE_CUSTOMER("customer");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
