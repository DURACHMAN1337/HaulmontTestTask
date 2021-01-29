package org.dentech.Repository;

import org.dentech.Entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends JpaRepository<Bank,Long> {
}
