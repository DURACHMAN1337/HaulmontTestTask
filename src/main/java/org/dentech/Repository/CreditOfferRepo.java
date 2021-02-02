package org.dentech.Repository;

import org.dentech.Entities.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditOfferRepo extends JpaRepository<CreditOffer,Long> {

    @Query("select p from CreditOffer p where p.bankId =:bankID")
    List<CreditOffer> findAllOffersForClient(@Param("bankID") long bankID);
}
