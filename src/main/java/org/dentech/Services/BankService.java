package org.dentech.Services;

import org.dentech.Entities.Bank;
import org.dentech.Entities.Client;
import org.dentech.Entities.Credit;
import org.dentech.Repository.BankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    final BankRepo bankRepo;

    @Autowired
    public BankService(BankRepo bankRepo) {
        this.bankRepo = bankRepo;
    }

    public void delete(Bank bank) {
        bankRepo.delete(bank);
        System.out.println("Delete client from bank");
    }

    public void deleteById(Long id) {
        bankRepo.deleteById(id);
        System.out.println("Delete client from bank (by BANK_ID)");
    }


    public List<Bank> getAll() {
        return bankRepo.findAll();
    }

    public Optional<Bank> getBank(Client client, Credit credit) {
        Bank bank = new Bank();
        bank.setClient(client);
        bank.setBankCredit(credit);
        return bankRepo.findOne(Example.of(bank));
    }

    public void save(Bank bank) {
        bankRepo.save(bank);
        System.out.println("Saving a new client in the bank ");
    }

}
