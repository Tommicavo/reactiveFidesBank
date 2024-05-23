package it.fides.user.models.dtos;

public class UserDto {

    private Long idUser;
    private String firstNameUser;
    private String lastNameUser;
    private String emailUser;
    private String passwordUser;
    private RoleDto role;

    public UserDto() {}

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

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }
}
