package it.aguzzo.diarioalimentare.service.mapper;

import it.aguzzo.diarioalimentare.domain.*;
import it.aguzzo.diarioalimentare.service.dto.AlimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alimento} and its DTO {@link AlimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlimentoMapper extends EntityMapper<AlimentoDTO, Alimento> {



    default Alimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Alimento alimento = new Alimento();
        alimento.setId(id);
        return alimento;
    }
}
