package org.dentech.Entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "credits")
public class Credit extends AbstractEntityClass implements Comparable<Credit> {

    @NotNull
    @Column(name = "credit_limit")
    private Long creditLimit;

    @NotNull
    @Column(name = "credit_percent")
    private Double creditPercent;

    public Credit() {
    }

    public Credit(Long creditLimit, Double creditPercent) {
        this.creditLimit = creditLimit;
        this.creditPercent = creditPercent;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "creditLimit=" + this.creditLimit +
                ", creditPercent=" + this.creditPercent +
                '}';
    }

    @Override
    public int compareTo(Credit credit) {
        return  this.getCreditLimit().compareTo(credit.getCreditLimit());
    }

}
