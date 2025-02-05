package dto;

import java.util.UUID;

/**
 * Clasa retine informatiile unei programari din ziua curenta pentru meniul de medic
 */
public class ProgramarePanouMedicDto {
    private String nume;
    private String oraInceput;
    private String tip;
    private String stare;
    private UUID idProgramare;

    /**
     * Constructor
     * @param nume
     * @param oraInceput
     * @param tip
     * @param stare
     * @param idProgramare
     */
    public ProgramarePanouMedicDto(String nume, String oraInceput, String tip, String stare, UUID idProgramare) {
        this.nume = nume;
        this.oraInceput = oraInceput;
        this.tip = tip;
        this.stare = stare;
        this.idProgramare = idProgramare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(String oraInceput) {
        this.oraInceput = oraInceput;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    public UUID getIdProgramare() {
        return idProgramare;
    }

    public void setIdProgramare(UUID idProgramare) {
        this.idProgramare = idProgramare;
    }

    @Override
    public String toString() {
        return "ProgramarePanouMedicDto{" +
                "nume='" + nume + '\'' +
                ", oraInceput=" + oraInceput +
                ", tip='" + tip + '\'' +
                ", stare='" + stare + '\'' +
                ", idProgramare=" + idProgramare +
                '}';
    }
}
