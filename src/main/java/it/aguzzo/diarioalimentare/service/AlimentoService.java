package it.aguzzo.diarioalimentare.service;

import it.aguzzo.diarioalimentare.service.dto.AlimentoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.aguzzo.diarioalimentare.domain.Alimento}.
 */
public interface AlimentoService {

    /**
     * Save a alimento.
     *
     * @param alimentoDTO the entity to save.
     * @return the persisted entity.
     */
    AlimentoDTO save(AlimentoDTO alimentoDTO);

    /**
     * Get all the alimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlimentoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alimento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlimentoDTO> findOne(Long id);

    /**
     * Delete the "id" alimento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
