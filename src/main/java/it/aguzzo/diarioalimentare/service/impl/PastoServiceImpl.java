package it.aguzzo.diarioalimentare.service.impl;

import it.aguzzo.diarioalimentare.service.PastoService;
import it.aguzzo.diarioalimentare.domain.Pasto;
import it.aguzzo.diarioalimentare.repository.PastoRepository;
import it.aguzzo.diarioalimentare.service.dto.PastoDTO;
import it.aguzzo.diarioalimentare.service.mapper.PastoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pasto}.
 */
@Service
@Transactional
public class PastoServiceImpl implements PastoService {

    private final Logger log = LoggerFactory.getLogger(PastoServiceImpl.class);

    private final PastoRepository pastoRepository;

    private final PastoMapper pastoMapper;

    public PastoServiceImpl(PastoRepository pastoRepository, PastoMapper pastoMapper) {
        this.pastoRepository = pastoRepository;
        this.pastoMapper = pastoMapper;
    }

    /**
     * Save a pasto.
     *
     * @param pastoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PastoDTO save(PastoDTO pastoDTO) {
        log.debug("Request to save Pasto : {}", pastoDTO);
        Pasto pasto = pastoMapper.toEntity(pastoDTO);
        pasto = pastoRepository.save(pasto);
        return pastoMapper.toDto(pasto);
    }

    /**
     * Get all the pastos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PastoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pastos");
        return pastoRepository.findAll(pageable)
            .map(pastoMapper::toDto);
    }


    /**
     * Get one pasto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PastoDTO> findOne(Long id) {
        log.debug("Request to get Pasto : {}", id);
        return pastoRepository.findById(id)
            .map(pastoMapper::toDto);
    }

    /**
     * Delete the pasto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pasto : {}", id);
        pastoRepository.deleteById(id);
    }
}
