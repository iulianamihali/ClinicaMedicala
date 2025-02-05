package dto;

import enums.TipUtilizatorEnum;

import java.io.Serializable;
import java.util.UUID;

/**
 * Clasa retine informatiile despre sesiunea de logare a unui utilizator
 */
public class InformatiiSesiuneDto implements Serializable {
    private UUID id;
    private TipUtilizatorEnum tip;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * @param id
     * @param tip
     */
    public InformatiiSesiuneDto(UUID id, TipUtilizatorEnum tip) {
        this.id = id;
        this.tip = tip;
    }

    public InformatiiSesiuneDto() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipUtilizatorEnum getTip() {
        return tip;
    }

    public void setTip(TipUtilizatorEnum tip) {
        this.tip = tip;
    }

    public boolean esteValid() {
        return id != null && tip != null;
    }

    @Override
    public String toString() {
        return "InformatiiSesiuneDto{" +
                "id=" + id +
                ", tip=" + tip +
                '}';
    }
}
