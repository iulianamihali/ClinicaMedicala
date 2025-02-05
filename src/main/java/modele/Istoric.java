package modele;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clasa reprezinta informatiile legate de istoricul unei programari la clinica
 * @author Mihali Iuliana-Calina
 */
public class Istoric {
    private UUID id;
    private UUID idProgramare;
    private LocalDateTime ultimaModificare;
    private double cost;
    private String simptome;
    private String diagnostic;
    private String rezultateAnalize;
    private String tratament;

    /**
     * Constructor
     * @param id
     * @param idProgramare
     * @param ultimaModificare
     * @param cost
     * @param simptome
     * @param diagnostic
     * @param rezultateAnalize
     * @param tratament
     */
    public Istoric(UUID id, UUID idProgramare, LocalDateTime ultimaModificare, double cost, String simptome, String diagnostic, String rezultateAnalize, String tratament) {
        this.id = id;
        this.idProgramare = idProgramare;
        this.ultimaModificare = ultimaModificare;
        this.cost = cost;
        this.simptome = simptome;
        this.diagnostic = diagnostic;
        this.rezultateAnalize = rezultateAnalize;
        this.tratament = tratament;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdProgramare() {
        return idProgramare;
    }

    public void setIdProgramare(UUID idProgramare) {
        this.idProgramare = idProgramare;
    }

    public LocalDateTime getUltimaModificare() {
        return ultimaModificare;
    }

    public void setUltimaModificare(LocalDateTime ultimaModificare) {
        this.ultimaModificare = ultimaModificare;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getSimptome() {
        return simptome;
    }

    public void setSimptome(String simptome) {
        this.simptome = simptome;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getRezultateAnalize() {
        return rezultateAnalize;
    }

    public void setRezultateAnalize(String rezultateAnalize) {
        this.rezultateAnalize = rezultateAnalize;
    }

    public String getTratament() {
        return tratament;
    }

    public void setTratament(String tratament) {
        this.tratament = tratament;
    }

    /**
     * Metoda care returneaza informatiile despre istoricul programarii
     * @return un obiect de tip String care contine detaliile istoricului
     */
    @Override
    public String toString() {
        return "Istoric{" +
                "id=" + id +
                ", idProgramare=" + idProgramare +
                ", ultimaModificare=" + ultimaModificare +
                ", cost=" + cost +
                ", simptome='" + simptome + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", rezultateAnalize='" + rezultateAnalize + '\'' +
                ", tratament='" + tratament + '\'' +
                '}';
    }
}
