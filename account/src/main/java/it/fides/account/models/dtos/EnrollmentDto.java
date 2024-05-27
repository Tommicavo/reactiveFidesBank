package it.fides.account.models.dtos;

import java.util.List;

public class EnrollmentDto {

    private Long idAccount;
    private List<Long> idOwners;

    public EnrollmentDto() {}
    public EnrollmentDto(Long idAccount, List<Long> idOwners) {
        this.idAccount = idAccount;
        this.idOwners = idOwners;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public List<Long> getIdOwners() {
        return idOwners;
    }

    public void setIdOwners(List<Long> idOwners) {
        this.idOwners = idOwners;
    }
}
