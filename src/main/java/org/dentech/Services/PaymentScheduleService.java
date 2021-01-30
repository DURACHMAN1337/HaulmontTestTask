package org.dentech.Services;

import org.dentech.Entities.PaymentSchedule;
import org.dentech.Repository.PaymentScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentScheduleService {

    @Autowired
    PaymentScheduleRepo paymentScheduleRepo;

    public PaymentScheduleService() {
    }

    public void delete(PaymentSchedule paymentSchedule){
        paymentScheduleRepo.delete(paymentSchedule);
    }

    public void deleteById(Long id){
        paymentScheduleRepo.deleteById(id);
    }

    public List<PaymentSchedule> getAll(){
        return paymentScheduleRepo.findAll();
    }

    public void save(PaymentSchedule paymentSchedule){
        paymentScheduleRepo.save(paymentSchedule);
    }

}
