package org.dentech.Repository;

import org.dentech.Entities.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOfferRepo extends JpaRepository<CreditOffer,Long> {
}
