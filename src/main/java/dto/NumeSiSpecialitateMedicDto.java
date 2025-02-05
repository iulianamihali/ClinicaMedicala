package dto;

import java.util.UUID;

/**
 * Clasa retine numele si specialitatea unui utilizator medic
 */
public class NumeSiSpecialitateMedicDto {
    private String nume;
    private String specialitate;
    private UUID idMedic;

    /**
     * Constructor
     * @param nume
     * @param specialitate
     * @param idMedic
     */
    public NumeSiSpecialitateMedicDto(String nume, String specialitate, UUID idMedic) {
        this.nume = nume;
        this.idMedic = idMedic;
        this.specialitate = specialitate;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    public String getSpecialitate() {
        return specialitate;
    }

    public void setSpecialitate(String specialitate) {
        this.specialitate = specialitate;
    }

    public UUID getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(UUID idMedic) {
        this.idMedic = idMedic;
    }

    @Override
    public String toString() {
        return "NumeSiSpecialitateMedicDto{" +
                "nume='" + nume + '\'' +
                ", specialitate='" + specialitate + '\'' +
                ", idMedic='" + idMedic + '\'' +
                '}';
    }
}
