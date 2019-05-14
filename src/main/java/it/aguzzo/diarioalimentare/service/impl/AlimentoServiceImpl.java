package it.aguzzo.diarioalimentare.service.impl;

import it.aguzzo.diarioalimentare.service.AlimentoService;
import it.aguzzo.diarioalimentare.domain.Alimento;
import it.aguzzo.diarioalimentare.repository.AlimentoRepository;
import it.aguzzo.diarioalimentare.service.dto.AlimentoDTO;
import it.aguzzo.diarioalimentare.service.mapper.AlimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Alimento}.
 */
@Service
@Transactional
public class AlimentoServiceImpl implements AlimentoService {

    private final Logger log = LoggerFactory.getLogger(AlimentoServiceImpl.class);

    private final AlimentoRepository alimentoRepository;

    private final AlimentoMapper alimentoMapper;

    public AlimentoServiceImpl(AlimentoRepository alimentoRepository, AlimentoMapper alimentoMapper) {
        this.alimentoRepository = alimentoRepository;
        this.alimentoMapper = alimentoMapper;
    }

    /**
     * Save a alimento.
     *
     * @param alimentoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AlimentoDTO save(AlimentoDTO alimentoDTO) {
        log.debug("Request to save Alimento : {}", alimentoDTO);
        Alimento alimento = alimentoMapper.toEntity(alimentoDTO);
        alimento = alimentoRepository.save(alimento);
        return alimentoMapper.toDto(alimento);
    }

    /**
     * Get all the alimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alimentos");
        return alimentoRepository.findAll(pageable)
            .map(alimentoMapper::toDto);
    }


    /**
     * Get one alimento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlimentoDTO> findOne(Long id) {
        log.debug("Request to get Alimento : {}", id);
        return alimentoRepository.findById(id)
            .map(alimentoMapper::toDto);
    }

    /**
     * Delete the alimento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alimento : {}", id);
        alimentoRepository.deleteById(id);
    }
}
