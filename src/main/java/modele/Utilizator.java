package modele;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clasa reprezinta un pacient in aplicatie
 * @author Mihali Iuliana-Calina
 */
public class Utilizator {
    protected UUID id;
    protected String nume;
    protected String prenume;
    protected LocalDate dataNasterii;
    protected String gen;
    protected String adresa;
    protected String email;
    protected String parola;
    protected String telefon;
    protected String cnp;
    protected String seria;

    /**
     * Constructor
     * @param id
     * @param nume
     * @param prenume
     * @param dataNasterii
     * @param gen
     * @param adresa
     * @param email
     * @param parola
     * @param telefon
     * @param cnp
     * @param seria
     */
    public Utilizator(UUID id, String nume, String prenume, LocalDate dataNasterii, String gen, String adresa, String email, String parola, String telefon, String cnp, String seria) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.dataNasterii = dataNasterii;
        this.gen = gen;
        this.adresa = adresa;
        this.email = email;
        this.parola = parola;
        this.telefon = telefon;
        this.cnp = cnp;
        this.seria = seria;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDate getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(LocalDate dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
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

    /**
     * Metoda creeaza un String a obiectului Utilizator cu toate informatiile acestuia
     * @return un String cu informatiile despre pacient
     */
    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", dataNasterii=" + dataNasterii +
                ", gen='" + gen + '\'' +
                ", adresa='" + adresa + '\'' +
                ", email='" + email + '\'' +
                ", parola='" + parola + '\'' +
                ", telefon='" + telefon + '\'' +
                ", cnp='" + cnp + '\'' +
                ", seria='" + seria + '\'' +
                '}';
    }
}
