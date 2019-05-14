package it.aguzzo.diarioalimentare.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * JHipster JDL model for diarioAlimentare
 */
@Entity
@Table(name = "pasto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pasto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "quantita")
    private Integer quantita;

    @OneToOne
    @JoinColumn(unique = true)
    private Alimento alimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public Pasto data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public Pasto quantita(Integer quantita) {
        this.quantita = quantita;
        return this;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public Pasto alimento(Alimento alimento) {
        this.alimento = alimento;
        return this;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pasto)) {
            return false;
        }
        return id != null && id.equals(((Pasto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pasto{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", quantita=" + getQuantita() +
            "}";
    }
}
