package org.dentech.Entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "credit_offers")
public class CreditOffer extends AbstractEntityClass {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @NotNull
    @Column(name = "credit_amount")
    private Long creditAmount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_schedule_id")
    private PaymentSchedule paymentSchedule;

    @NotNull
    @Column(name = "bank_id")
    private long bankId;

    public CreditOffer() {
    }

    public CreditOffer(Client client, Credit credit, Long creditAmount, PaymentSchedule paymentSchedule, long bankId) {
        this.client = client;
        this.credit = credit;
        this.creditAmount = creditAmount;
        this.paymentSchedule = paymentSchedule;
        this.bankId = bankId;
    }
}
