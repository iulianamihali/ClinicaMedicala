package dto;

import enums.TipUtilizatorEnum;

import java.util.UUID;

/**
 * Clasa retine informatiile utile pentru un utilizator logat
 */
// DataTransferObject -> din clasa Utilizator retin doar informatiile necesare
public class UtilizatorDto {
    private UUID id;
    private String email;
    private String parola;
    private String nume;
    private String prenume;
    private String specializare;
    private TipUtilizatorEnum tipUtilizator;

    /**
     * Constructor
     * @param id
     * @param email
     * @param parola
     * @param nume
     * @param prenume
     * @param specializare
     * @param tipUtilizator
     */
    public UtilizatorDto(UUID id, String email, String parola, String nume, String prenume, String specializare, TipUtilizatorEnum tipUtilizator) {
        this.id = id;
        this.email = email;
        this.parola = parola;
        this.nume = nume;
        this.prenume = prenume;
        this.specializare = specializare;
        this.tipUtilizator = tipUtilizator;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getNume()
    {
        return this.nume;
    }

    public void setNume(String nume)
    {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public TipUtilizatorEnum getTipUtilizator() {
        return tipUtilizator;
    }

    public void setTipUtilizator(TipUtilizatorEnum tipUtilizator) {
        this.tipUtilizator = tipUtilizator;
    }
}
