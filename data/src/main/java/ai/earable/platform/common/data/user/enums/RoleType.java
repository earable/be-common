package ai.earable.platform.common.data.user.enums;

public enum RoleType {
    ROLE_ADMIN,
    ROLE_SUB_ADMIN,
    ROLE_USER,
    ROLE_CUSTOMER_ADMIN,
    ROLE_CUSTOMER_TECHNICIAN,
    ROLE_CUSTOMER_SUPER_ADMIN,
    ROLE_CUSTOMER;

    public String getAuthority() {
        return this.name().substring("ROLE_".length());
    }
}