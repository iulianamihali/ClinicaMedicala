package dto;

import java.util.UUID;

/**
 * Clasa retine informatiile despre pacienti pentru meniul de medic
 */
public class TabelPacientiMedicDto {
    private UUID idPacient;
    private String nume;
    private String gen;
    private String telefon;
    private int totalProgramari;

    /**
     * Constructor
     * @param idPacient
     * @param nume
     * @param gen
     * @param telefon
     * @param totalProgramari
     */
    public TabelPacientiMedicDto(UUID idPacient, String nume, String gen, String telefon, int totalProgramari) {
        this.idPacient = idPacient;
        this.nume = nume;
        this.gen = gen;
        this.telefon = telefon;
        this.totalProgramari = totalProgramari;
    }

    public UUID getIdPacient() {
        return idPacient;
    }

    public void setIdPacient(UUID idPacient) {
        this.idPacient = idPacient;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getTotalProgramari() {
        return totalProgramari;
    }

    public void setTotalProgramari(int totalProgramari) {
        this.totalProgramari = totalProgramari;
    }

    @Override
    public String toString() {
        return "TabelPacientiMedicDto{" +
                "nume='" + nume + '\'' +
                ", gen='" + gen + '\'' +
                ", telefon='" + telefon + '\'' +
                ", totalProgramari=" + totalProgramari +
                '}';
    }
}
