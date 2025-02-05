package modele;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clasa care reprezinta informatiile legate de programarea unui pacient la un medic
 * @author Mihali Iuliana-Calina
 */
public class Programare {
    private UUID id;
    private UUID idUtilizator;
    private UUID idMedic;
    private LocalDate dataCreare;
    private String locatie;
    private String tip;
    private String stare;
    private String oraInceput;

    /**
     * Constructor
     * @param id
     * @param idUtilizator
     * @param idMedic
     * @param dataCreare
     * @param locatie
     * @param tip
     * @param stare
     * @param oraInceput
     */
    public Programare(UUID id, UUID idUtilizator,  UUID idMedic, LocalDate dataCreare, String locatie, String tip, String stare, String oraInceput) {
        this.id = id;
        this.idUtilizator = idUtilizator;
        this.idMedic = idMedic;
        this.dataCreare = dataCreare;
        this.locatie = locatie;
        this.tip = tip;
        this.stare = stare;
        this.oraInceput = oraInceput;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(UUID idMedic) {
        this.idMedic = idMedic;
    }

    public UUID getIdUtilizator() {
        return idUtilizator;
    }

    public void setIdUtilizator(UUID idUtilizator) {
        this.idUtilizator = idUtilizator;
    }

    public LocalDate getDataCreare() {
        return dataCreare;
    }

    public void setDataCreare(LocalDate dataCreare) {
        this.dataCreare = dataCreare;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getStare() {
        return this.stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(String oraInceput) {
        this.oraInceput = oraInceput;
    }

    /**
     * Metoda face un text care contine toate informatiile despre programare
     * @return obict de tip String cu datele programarii
     */
    @Override
    public String toString() {
        return "Programare{" +
                "id=" + id +
                ", idMedic=" + idMedic +
                ", idUtilizator=" + idUtilizator +
                ", dataCreare=" + dataCreare +
                ", locatie='" + locatie + '\'' +
                ", confirmare=" + stare +
                ", tip='" + tip + '\'' +
                ", oraInceput=" + oraInceput +
                '}';
    }
}
