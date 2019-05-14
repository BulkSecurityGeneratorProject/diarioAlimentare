package it.aguzzo.diarioalimentare.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.aguzzo.diarioalimentare.domain.Pasto} entity.
 */
@ApiModel(description = "JHipster JDL model for diarioAlimentare")
public class PastoDTO implements Serializable {

    private Long id;

    private LocalDate data;

    private Integer quantita;


    private Long alimentoId;

    private String alimentoNome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Long getAlimentoId() {
        return alimentoId;
    }

    public void setAlimentoId(Long alimentoId) {
        this.alimentoId = alimentoId;
    }

    public String getAlimentoNome() {
        return alimentoNome;
    }

    public void setAlimentoNome(String alimentoNome) {
        this.alimentoNome = alimentoNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PastoDTO pastoDTO = (PastoDTO) o;
        if (pastoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pastoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PastoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", quantita=" + getQuantita() +
            ", alimento=" + getAlimentoId() +
            ", alimento='" + getAlimentoNome() + "'" +
            "}";
    }
}
