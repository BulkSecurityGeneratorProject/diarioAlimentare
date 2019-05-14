package it.aguzzo.diarioalimentare.service;

import it.aguzzo.diarioalimentare.service.dto.PastoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.aguzzo.diarioalimentare.domain.Pasto}.
 */
public interface PastoService {

    /**
     * Save a pasto.
     *
     * @param pastoDTO the entity to save.
     * @return the persisted entity.
     */
    PastoDTO save(PastoDTO pastoDTO);

    /**
     * Get all the pastos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PastoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pasto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PastoDTO> findOne(Long id);

    /**
     * Delete the "id" pasto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
