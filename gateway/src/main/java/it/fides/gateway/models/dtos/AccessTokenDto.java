package it.fides.gateway.models.dtos;

import java.util.Date;

public class AccessTokenDto {

    private String token;
    private Long idUser;
    private Date expiration;
    private String labelRole;
    private boolean isVerified;
    private String errorMessage;

    public AccessTokenDto() {}
    public AccessTokenDto(boolean isVerified, String errorMessage) {
        this.isVerified = isVerified;
        this.errorMessage = errorMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getLabelRole() {
        return labelRole;
    }

    public void setLabelRole(String labelRole) {
        this.labelRole = labelRole;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "AccessTokenDto{" +
                "token='" + token + '\'' +
                ", idUser=" + idUser +
                ", expiration=" + expiration +
                ", labelRole='" + labelRole + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}
