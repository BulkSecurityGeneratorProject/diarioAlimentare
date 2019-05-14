package it.aguzzo.diarioalimentare.repository;

import it.aguzzo.diarioalimentare.domain.Pasto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PastoRepository extends JpaRepository<Pasto, Long> {

}
