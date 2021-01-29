package org.dentech.Repository;

import org.dentech.Entities.PaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentScheduleRepo extends JpaRepository<PaymentSchedule,Long> {
}
