package it.fides.account.models.enums;

public enum RoleEnum {

    DEFAULT(1L, "DEFAULT"),
    ADMIN(2L, "ADMIN");

    private final Long id;
    private final String label;

    RoleEnum(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
