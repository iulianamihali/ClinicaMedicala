package dto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clasa retine informatiile despre istoricul unei programai pentru afisarea la meniul de medic
 */
public class IstoricTabelDto {
    private UUID idProgramare;
    private String nume;
    private String prenume;
    private String gen;
    private String telefon;
    private LocalDate dataProgramarii;

    /**
     * Constructor
     * @param idProgramare
     * @param nume
     * @param prenume
     * @param gen
     * @param telefon
     * @param dataProgramarii
     */
    public IstoricTabelDto(UUID idProgramare, String nume, String prenume, String gen, String telefon, LocalDate dataProgramarii) {
        this.idProgramare = idProgramare;
        this.nume = nume;
        this.prenume = prenume;
        this.gen = gen;
        this.telefon = telefon;
        this.dataProgramarii = dataProgramarii;
    }

    public UUID getIdProgramare() {
        return idProgramare;
    }

    public void setIdProgramare(UUID idProgramare) {
        this.idProgramare = idProgramare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
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

    public LocalDate getDataProgramarii() {
        return dataProgramarii;
    }

    public void setDataProgramarii(LocalDate dataProgramarii) {
        this.dataProgramarii = dataProgramarii;
    }

    @Override
    public String toString() {
        return "IstoricTabelDto{" +
                "idProgramare=" + idProgramare +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", gen='" + gen + '\'' +
                ", telefon='" + telefon + '\'' +
                ", dataProgramarii=" + dataProgramarii +
                '}';
    }
}
