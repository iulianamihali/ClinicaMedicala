package dto;

import enums.TipUtilizatorEnum;
import java.io.*;
import java.util.UUID;

/**
 * Clasa se ocupa cu realizarea sesiuni de logare
 */
public class SesiuneLogareDto {
    /**
     * Salvarea prin serializare a utilizatorului logat pentru a nu fi nevoit sa se logeze din nou la pornirea apliactiei
     * @param id
     * @param tip
     */
    public static void salvareSesiune(UUID id, TipUtilizatorEnum tip) {
        String sesiuneFisier = "resources/sesiune/fisierSesiune.bin";

        try {
            File file = new File(sesiuneFisier);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            InformatiiSesiuneDto informatiiSesiuneDto = new InformatiiSesiuneDto(id, tip);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(informatiiSesiuneDto);
                System.out.println("Sesiunea a fost salvata cu succes in: " + sesiuneFisier);
            }
        } catch (Exception e) {
            System.err.println("Eroare la salvarea sesiunii: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Citirea datelor la pronirea aplicatiei din fisier
     * @return
     */
    public static InformatiiSesiuneDto citireFisier() {
        String sesiuneFisier = "resources/sesiune/fisierSesiune.bin";
        File file = new File(sesiuneFisier);

        if (!file.exists() || file.length() == 0) {
            System.out.println("Fișierul de sesiune este gol sau nu există. Se va crea o sesiune goală.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            InformatiiSesiuneDto infoSesiune = (InformatiiSesiuneDto) ois.readObject();
            return infoSesiune;
        } catch (Exception e) {
            System.err.println("Eroare la citirea fișierului de sesiune: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Stergearea datelor din fisier la deconectare
     */
    public static void stergereFisierSesiune()
    {
        String sesiuneFisier = "resources/sesiune/fisierSesiune.bin";
        try {
            File file = new File(sesiuneFisier);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(null);
                System.out.println("Sesiunea a fost stearsa cu succes: " + sesiuneFisier);
            }
        } catch (Exception e) {
            System.err.println("Eroare la stergerea sesiunii: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
