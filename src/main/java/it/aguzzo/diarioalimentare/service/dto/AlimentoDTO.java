package it.aguzzo.diarioalimentare.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.aguzzo.diarioalimentare.domain.Alimento} entity.
 */
public class AlimentoDTO implements Serializable {

    private Long id;

    private String nome;

    private String descrizione;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlimentoDTO alimentoDTO = (AlimentoDTO) o;
        if (alimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlimentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
