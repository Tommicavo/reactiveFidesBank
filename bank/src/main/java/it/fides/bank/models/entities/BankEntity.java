package it.fides.bank.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "bank")
public class BankEntity {

    @Id
    @Column("id_bank")
    private Long idBank;

    @Column("name")
    private String nameBank;

    public BankEntity() {}

    public Long getIdBank() {
        return idBank;
    }

    public void setIdBank(Long idBank) {
        this.idBank = idBank;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }
}
