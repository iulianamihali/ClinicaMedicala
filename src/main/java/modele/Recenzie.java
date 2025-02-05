package modele;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clasa care reprezinta feedback-ul oferit de un pacient pentru un medic
 * @author Mihali Iuliana-Calina
 */
public class Recenzie {
    private UUID id;
    private UUID idMedic;
    private UUID idUtilizator;
    private String descriere;
    private int nota;
    private LocalDateTime dataCreare;

    /**
     * Constructor
     * @param id
     * @param idMedic
     * @param idUtilizator
     * @param descriere
     * @param nota
     * @param dataCreare
     */
    public Recenzie(UUID id, UUID idMedic, UUID idUtilizator, String descriere, int nota, LocalDateTime dataCreare) {
        this.id = id;
        this.idMedic = idMedic;
        this.idUtilizator = idUtilizator;
        this.descriere = descriere;
        this.nota = nota;
        this.dataCreare = dataCreare;
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

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataCreare() {
        return dataCreare;
    }

    public void setDataCreare(LocalDateTime dataCreare) {
        this.dataCreare = dataCreare;
    }

    /**
     * Metoda creeaza un text cu toate informatiile despre recenzie
     * @return un String cu datele recenziei
     */
    @Override
    public String toString() {
        return "Recenzie{" +
                "id=" + id +
                ", idMedic=" + idMedic +
                ", idUtilizator=" + idUtilizator +
                ", descriere='" + descriere + '\'' +
                ", nota=" + nota +
                ", dataCreare=" + dataCreare +
                '}';
    }
}
