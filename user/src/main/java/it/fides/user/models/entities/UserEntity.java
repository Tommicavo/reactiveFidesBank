package it.fides.user.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
public class UserEntity {

    @Id
    @Column("id_user")
    private Long idUser;

    @Column("first_name")
    private String firstNameUser;

    @Column("last_name")
    private String lastNameUser;

    @Column("email")
    private String emailUser;

    @Column("password")
    private String passwordUser;

    @Column("id_role")
    private Long idRole;

    public UserEntity() {}

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getFirstNameUser() {
        return firstNameUser;
    }

    public void setFirstNameUser(String firstNameUser) {
        this.firstNameUser = firstNameUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "idUser=" + idUser +
                ", firstNameUser='" + firstNameUser + '\'' +
                ", lastNameUser='" + lastNameUser + '\'' +
                ", emailUser='" + emailUser + '\'' +
                ", passwordUser='" + passwordUser + '\'' +
                ", idRole=" + idRole +
                '}';
    }
}
