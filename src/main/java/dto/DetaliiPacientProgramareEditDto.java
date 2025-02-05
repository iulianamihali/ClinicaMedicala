package dto;

import java.time.LocalDate;

/**
 * Clasa retine detaliile unui pacient cand se creaza o programare
 */
public class DetaliiPacientProgramareEditDto {
    private String nume;
    private String prenume;
    private String gen;
    private int varsta;
    private LocalDate dataN;
    private String email;
    private String telefon;
    private String cnp;
    private String seria;

    /**
     * Contructor
     * @param nume
     * @param prenume
     * @param gen
     * @param varsta
     * @param dataN
     * @param email
     * @param telefon
     * @param cnp
     * @param seria
     */
    public DetaliiPacientProgramareEditDto(String nume, String prenume, String gen, int varsta, LocalDate dataN, String email, String telefon, String cnp, String seria) {
        this.nume = nume;
        this.prenume = prenume;
        this.gen = gen;
        this.varsta = varsta;
        this.dataN = dataN;
        this.email = email;
        this.telefon = telefon;
        this.cnp = cnp;
        this.seria = seria;
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

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public LocalDate getDataN() {
        return dataN;
    }

    public void setDataN(LocalDate dataN) {
        this.dataN = dataN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    @Override
    public String toString() {
        return "DetaliiPacientProgramareEditDto{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", gen='" + gen + '\'' +
                ", varsta=" + varsta +
                ", dataN='" + dataN + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", cnp='" + cnp + '\'' +
                ", seria='" + seria + '\'' +
                '}';
    }
}
