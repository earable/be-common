package ai.earable.platform.common.data.user.enums;

public enum RoleType {
    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_USER,
    ROLE_CUSTOMER;

    public String getAuthority() {
        return this.name().substring("ROLE_".length());
    }
}