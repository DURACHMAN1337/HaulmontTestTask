package org.dentech.Repository;

import org.dentech.Entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepo extends JpaRepository<Credit,Long> {
}
