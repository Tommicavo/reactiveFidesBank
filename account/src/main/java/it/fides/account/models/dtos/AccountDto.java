package it.fides.account.models.dtos;

import java.util.List;

public class AccountDto {

    private Long idAccount;
    private String nameAccount;
    private long amountAccount;
    private Long idBank;
    private List<Long> coOwnersList;

    public AccountDto() {}

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public long getAmountAccount() {
        return amountAccount;
    }

    public void setAmountAccount(long amountAccount) {
        this.amountAccount = amountAccount;
    }

    public Long getIdBank() {
        return idBank;
    }

    public void setIdBank(Long idBank) {
        this.idBank = idBank;
    }

    public List<Long> getCoOwnersList() {
        return coOwnersList;
    }

    public void setCoOwnersList(List<Long> coOwnersList) {
        this.coOwnersList = coOwnersList;
    }
}
