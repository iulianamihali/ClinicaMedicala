package dto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clasa retine informatiile despre istoric a unui pacient
 */
public class IstoricPacientTabelDto {
    private UUID idProgramare;
    private LocalDate data;
    private double cost;
    private String numeMedic;
    private String specialitate;

    /**
     * Constructor
     * @param idProgramare
     * @param data
     * @param cost
     * @param numeMedic
     * @param specialitate
     */
    public IstoricPacientTabelDto(UUID idProgramare, LocalDate data, double cost, String numeMedic, String specialitate) {
        this.idProgramare = idProgramare;
        this.data = data;
        this.cost = cost;
        this.numeMedic = numeMedic;
        this.specialitate = specialitate;
    }

    public UUID getIdProgramare() {
        return idProgramare;
    }

    public void setIdProgramare(UUID idProgramare) {
        this.idProgramare = idProgramare;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNumeMedic() {
        return numeMedic;
    }

    public void setNumeMedic(String numeMedic) {
        this.numeMedic = numeMedic;
    }

    public String getSpecialitate() {
        return specialitate;
    }

    public void setSpecialitate(String specialitate) {
        this.specialitate = specialitate;
    }

    @Override
    public String toString() {
        return "IstoricPacientTabelDto{" +
                "idProgramare=" + idProgramare +
                ", data=" + data +
                ", cost=" + cost +
                ", numeMedic='" + numeMedic + '\'' +
                ", specialitate='" + specialitate + '\'' +
                '}';
    }
}
