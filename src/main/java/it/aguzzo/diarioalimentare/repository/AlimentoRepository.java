package it.aguzzo.diarioalimentare.repository;

import it.aguzzo.diarioalimentare.domain.Alimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Alimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {

}
