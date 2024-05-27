package it.fides.account.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "account")
public class AccountEntity {

    @Id
    @Column("id_account")
    private Long idAccount;

    @Column("name")
    private String nameAccount;

    @Column("amount")
    private long amountAccount;

    @Column("id_bank")
    private Long idBank;

    public AccountEntity() {}

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
}
