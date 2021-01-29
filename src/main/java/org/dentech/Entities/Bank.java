package org.dentech.Entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "banks")
public class Bank extends AbstractEntityClass {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Credit bankCredit;

    public Bank() {
    }

    public Bank(Client client, Credit bankCredit) {
        this.client = client;
        this.bankCredit = bankCredit;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "client=" + this.client +
                ", bankCredit=" + this.bankCredit +
                '}';
    }
}
