package it.aguzzo.diarioalimentare.web.rest;

import it.aguzzo.diarioalimentare.service.AlimentoService;
import it.aguzzo.diarioalimentare.web.rest.errors.BadRequestAlertException;
import it.aguzzo.diarioalimentare.service.dto.AlimentoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.aguzzo.diarioalimentare.domain.Alimento}.
 */
@RestController
@RequestMapping("/api")
public class AlimentoResource {

    private final Logger log = LoggerFactory.getLogger(AlimentoResource.class);

    private static final String ENTITY_NAME = "alimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlimentoService alimentoService;

    public AlimentoResource(AlimentoService alimentoService) {
        this.alimentoService = alimentoService;
    }

    /**
     * {@code POST  /alimentos} : Create a new alimento.
     *
     * @param alimentoDTO the alimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alimentoDTO, or with status {@code 400 (Bad Request)} if the alimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alimentos")
    public ResponseEntity<AlimentoDTO> createAlimento(@RequestBody AlimentoDTO alimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Alimento : {}", alimentoDTO);
        if (alimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new alimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlimentoDTO result = alimentoService.save(alimentoDTO);
        return ResponseEntity.created(new URI("/api/alimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alimentos} : Updates an existing alimento.
     *
     * @param alimentoDTO the alimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alimentoDTO,
     * or with status {@code 400 (Bad Request)} if the alimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alimentos")
    public ResponseEntity<AlimentoDTO> updateAlimento(@RequestBody AlimentoDTO alimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Alimento : {}", alimentoDTO);
        if (alimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlimentoDTO result = alimentoService.save(alimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alimentos} : get all the alimentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alimentos in body.
     */
    @GetMapping("/alimentos")
    public ResponseEntity<List<AlimentoDTO>> getAllAlimentos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Alimentos");
        Page<AlimentoDTO> page = alimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alimentos/:id} : get the "id" alimento.
     *
     * @param id the id of the alimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alimentos/{id}")
    public ResponseEntity<AlimentoDTO> getAlimento(@PathVariable Long id) {
        log.debug("REST request to get Alimento : {}", id);
        Optional<AlimentoDTO> alimentoDTO = alimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alimentoDTO);
    }

    /**
     * {@code DELETE  /alimentos/:id} : delete the "id" alimento.
     *
     * @param id the id of the alimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alimentos/{id}")
    public ResponseEntity<Void> deleteAlimento(@PathVariable Long id) {
        log.debug("REST request to delete Alimento : {}", id);
        alimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
