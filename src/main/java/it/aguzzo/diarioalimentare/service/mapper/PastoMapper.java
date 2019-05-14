package it.aguzzo.diarioalimentare.service.mapper;

import it.aguzzo.diarioalimentare.domain.*;
import it.aguzzo.diarioalimentare.service.dto.PastoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pasto} and its DTO {@link PastoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlimentoMapper.class})
public interface PastoMapper extends EntityMapper<PastoDTO, Pasto> {

    @Mapping(source = "alimento.id", target = "alimentoId")
    @Mapping(source = "alimento.nome", target = "alimentoNome")
    PastoDTO toDto(Pasto pasto);

    @Mapping(source = "alimentoId", target = "alimento")
    Pasto toEntity(PastoDTO pastoDTO);

    default Pasto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pasto pasto = new Pasto();
        pasto.setId(id);
        return pasto;
    }
}
