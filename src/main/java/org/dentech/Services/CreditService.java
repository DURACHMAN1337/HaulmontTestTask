package org.dentech.Services;

import org.dentech.Entities.Credit;
import org.dentech.Repository.CreditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CreditService {

    @Autowired
    CreditRepo creditRepo;

    public CreditService() {
    }

    public void delete(Credit credit) {
        creditRepo.delete(credit);
    }

    public void deleteById(Long id) {
        creditRepo.deleteById(id);
    }

    public List<Credit> getAll() {
        return creditRepo.findAll();
    }

    public List<Credit> getAllSort() {
        List<Credit> list = creditRepo.findAll();
        Collections.sort(list);
        return list;
    }

    public Optional<Credit> getCredit(Long creditAmount, Double creditProcent) {
        Credit credit = new Credit(creditAmount, creditProcent);
        return creditRepo.findOne(Example.of(credit));
    }

    public void save(Credit credit) {
        creditRepo.save(credit);
        System.out.println("Save Credit: " + credit.getCreditLimit() + ", " + credit.getCreditPercent());
    }
}
