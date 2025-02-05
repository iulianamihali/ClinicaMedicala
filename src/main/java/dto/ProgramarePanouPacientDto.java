package dto;

/**
 * Clasa retine informatiile depre o programare pentru meniul de pacient
 */
public class ProgramarePanouPacientDto {
    private String nume;
    private String prenume;
    private String ora;
    private String locatie;
    private String specialitate;
    private String stare;

    /**
     * Constructor
     * @param nume
     * @param prenume
     * @param ora
     * @param locatie
     * @param specialitate
     * @param stare
     */
    public ProgramarePanouPacientDto(String nume, String prenume, String ora, String locatie, String specialitate, String stare) {
        this.nume = nume;
        this.prenume = prenume;
        this.ora = ora;
        this.locatie = locatie;
        this.specialitate = specialitate;
        this.stare = stare;
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

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getSpecialitate() {
        return specialitate;
    }

    public void setSpecialitate(String specialitate) {
        this.specialitate = specialitate;
    }

    public String getStare() {
        return stare;
    }

    public void setStare(String stare) {
        this.stare = stare;
    }

    @Override
    public String toString() {
        return "ProgramarePanouPacientDto{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", ora='" + ora + '\'' +
                ", locatie='" + locatie + '\'' +
                ", specialitate='" + specialitate + '\'' +
                ", stare='" + stare + '\'' +
                '}';
    }
}
