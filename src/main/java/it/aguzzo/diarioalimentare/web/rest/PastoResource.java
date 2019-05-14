package it.aguzzo.diarioalimentare.web.rest;

import it.aguzzo.diarioalimentare.service.PastoService;
import it.aguzzo.diarioalimentare.web.rest.errors.BadRequestAlertException;
import it.aguzzo.diarioalimentare.service.dto.PastoDTO;

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
 * REST controller for managing {@link it.aguzzo.diarioalimentare.domain.Pasto}.
 */
@RestController
@RequestMapping("/api")
public class PastoResource {

    private final Logger log = LoggerFactory.getLogger(PastoResource.class);

    private static final String ENTITY_NAME = "pasto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PastoService pastoService;

    public PastoResource(PastoService pastoService) {
        this.pastoService = pastoService;
    }

    /**
     * {@code POST  /pastos} : Create a new pasto.
     *
     * @param pastoDTO the pastoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pastoDTO, or with status {@code 400 (Bad Request)} if the pasto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pastos")
    public ResponseEntity<PastoDTO> createPasto(@RequestBody PastoDTO pastoDTO) throws URISyntaxException {
        log.debug("REST request to save Pasto : {}", pastoDTO);
        if (pastoDTO.getId() != null) {
            throw new BadRequestAlertException("A new pasto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PastoDTO result = pastoService.save(pastoDTO);
        return ResponseEntity.created(new URI("/api/pastos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pastos} : Updates an existing pasto.
     *
     * @param pastoDTO the pastoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pastoDTO,
     * or with status {@code 400 (Bad Request)} if the pastoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pastoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pastos")
    public ResponseEntity<PastoDTO> updatePasto(@RequestBody PastoDTO pastoDTO) throws URISyntaxException {
        log.debug("REST request to update Pasto : {}", pastoDTO);
        if (pastoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PastoDTO result = pastoService.save(pastoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pastoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pastos} : get all the pastos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pastos in body.
     */
    @GetMapping("/pastos")
    public ResponseEntity<List<PastoDTO>> getAllPastos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Pastos");
        Page<PastoDTO> page = pastoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pastos/:id} : get the "id" pasto.
     *
     * @param id the id of the pastoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pastoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pastos/{id}")
    public ResponseEntity<PastoDTO> getPasto(@PathVariable Long id) {
        log.debug("REST request to get Pasto : {}", id);
        Optional<PastoDTO> pastoDTO = pastoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pastoDTO);
    }

    /**
     * {@code DELETE  /pastos/:id} : delete the "id" pasto.
     *
     * @param id the id of the pastoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pastos/{id}")
    public ResponseEntity<Void> deletePasto(@PathVariable Long id) {
        log.debug("REST request to delete Pasto : {}", id);
        pastoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
