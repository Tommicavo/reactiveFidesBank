package it.fides.gateway.models.entities;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "black_token")
public class BlackTokenEntity {

    @Column("id_black_token")
    private Long idBlackToken;

    @Column("value_black_token")
    private String valueBlackToken;

    public BlackTokenEntity() {}
    public BlackTokenEntity(String valueBlackToken) {
        this.valueBlackToken = valueBlackToken;
    }

    public Long getIdBlackToken() {
        return idBlackToken;
    }

    public void setIdBlackToken(Long idBlackToken) {
        this.idBlackToken = idBlackToken;
    }

    public String getValueBlackToken() {
        return valueBlackToken;
    }

    public void setValueBlackToken(String valueBlackToken) {
        this.valueBlackToken = valueBlackToken;
    }
}
