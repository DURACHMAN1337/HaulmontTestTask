package org.dentech.Entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "payment_schedule")
@EqualsAndHashCode(callSuper = true)
public class PaymentSchedule extends AbstractEntityClass {

    @NotNull
    @Column(name = "date_of_payment")
    private Date dateOfPayment;

    @NotNull
    @Column(name = "payment_amount")
    private Double paymentAmount;

    @NotNull
    @Column(name = "body_repayment_amount")
    private Double bodyRepayment;

    @NotNull
    @Column(name = "percent_repayment_amount")
    private Double percentRepayment;



}

