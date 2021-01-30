package org.dentech.Services;

import org.dentech.Entities.CreditOffer;
import org.dentech.Repository.CreditOfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditOfferService {

    @Autowired
    CreditOfferRepo creditOfferRepo;

    public CreditOfferService() {
    }

    public void delete(CreditOffer creditOffer){
        creditOfferRepo.delete(creditOffer);
    }

    public void deleteById(Long id){
        creditOfferRepo.deleteById(id);
    }

    public List<CreditOffer> getAll(){
        return creditOfferRepo.findAll();
    }

    public void save(CreditOffer creditOffer){
        creditOfferRepo.save(creditOffer);
    }
}
