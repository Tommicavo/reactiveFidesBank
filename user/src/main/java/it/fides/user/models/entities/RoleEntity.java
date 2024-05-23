package it.fides.user.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "role")
public class RoleEntity {

    @Id
    @Column("id_role")
    private Long idRole;

    @Column("label")
    private String labelRole;

    public RoleEntity() {}

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
