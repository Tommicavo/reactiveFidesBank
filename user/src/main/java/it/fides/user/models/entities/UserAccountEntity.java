package it.fides.user.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_account")
public class UserAccountEntity {

    @Id
    @Column("id_user_account")
    private Long idUserAccount;

    @Column("id_user")
    private Long idUser;

    @Column("id_account")
    private Long idAccount;

    public UserAccountEntity() {}

    public Long getIdUserAccount() {
        return idUserAccount;
    }

    public void setIdUserAccount(Long idUserAccount) {
        this.idUserAccount = idUserAccount;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }
}
