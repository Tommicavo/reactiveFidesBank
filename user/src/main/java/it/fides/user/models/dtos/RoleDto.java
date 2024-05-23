package it.fides.user.models.dtos;

public class RoleDto {

    private Long idRole;
    private String labelRole;

    public RoleDto() {}

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getLabelRole() {
        return labelRole;
    }

    public void setLabelRole(String labelRole) {
        this.labelRole = labelRole;
    }
}

