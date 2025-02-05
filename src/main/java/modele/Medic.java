package modele;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clasa Medic extinde clasa Utilizator si adauga informatii specifice unui medic
 */
public class Medic extends Utilizator{
    private String facultate;
    private String specializare;
    private int aniExperienta;
    private String programInceput;
    private String programFinal;
    private String descriere;

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
     * @param facultate
     * @param specializare
     * @param aniExperienta
     * @param programInceput
     * @param programFinal
     * @param descriere
     */
    public Medic(UUID id, String nume, String prenume, LocalDate dataNasterii, String gen, String adresa, String email, String parola, String telefon, String cnp, String seria, String facultate, String specializare, int aniExperienta, String programInceput, String programFinal, String descriere)
    {
        super(id, nume, prenume, dataNasterii, gen, adresa, email, parola, telefon, cnp, seria);
        this.facultate = facultate;
        this.specializare = specializare;
        this.aniExperienta = aniExperienta;
        this.programInceput = programInceput;
        this.programFinal = programFinal;
        this.descriere = descriere;
    }

    public String getFacultate() {
        return facultate;
    }

    public void setFacultate(String facultate) {
        this.facultate = facultate;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public int getAniExperienta() {
        return aniExperienta;
    }

    public void setAniExperienta(int aniExperienta) {
        this.aniExperienta = aniExperienta;
    }

    public String getProgramInceput() {
        return programInceput;
    }

    public void setProgramInceput(String programInceput) {
        this.programInceput = programInceput;
    }

    public String getProgramFinal() {
        return programFinal;
    }

    public void setProgramFinal(String programFinal) {
        this.programFinal = programFinal;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    /**
     * Metoda care returneaza informatiile
     * @return un String cu informatiile despre medic, inclusiv cele mostenite din Utilizator
     */
    @Override
    public String toString() {
        return "Medic{" +
                "facultate='" + facultate + '\'' +
                ", specializare='" + specializare + '\'' +
                ", aniExperienta=" + aniExperienta +
                ", programInceput='" + programInceput + '\'' +
                ", programFinal='" + programFinal + '\'' +
                ", descriere='" + descriere + '\'' +
                ", id=" + id +
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
