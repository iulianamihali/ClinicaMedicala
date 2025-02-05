package modele;

import dto.UtilizatorDto;
import enums.TipUtilizatorEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Clasa stocheaza toate informatiile din baza de date si se ocupa cu toate operatiile asupra bazei de date
 * @author Mihali Iuliana-Calina
 */
public class DbContext {
    public List<Utilizator> utilizatori;
    public List<Medic> medici;
    public List<Recenzie> recenzii;
    public List<Programare> programari;
    public List<Istoric> istoric;
    public UtilizatorDto utilizatorLogat;

    /**
     * Constructor
     */
    public DbContext() {
        this.utilizatori = new ArrayList<>();
        this.medici = new ArrayList<>();
        this.recenzii = new ArrayList<>();
        this.programari = new ArrayList<>();
        this.istoric = new ArrayList<>();
        selectUtilizatoriDb();
        selectMediciDb();
        selectRecenziiDb();
        selectProgramariDb();
        selectIstoricDb();
    }

    /**
     * Metoda selecteaza toti utilizatorii din baza de date, facem obiecte de tip Utilizator si apoi ii stochez in array-ul de utilizatori
     */
    public void selectUtilizatoriDb() {
        try {
            // am creat un obiect de tip Statement pentru a trimite interogari SQL catre baza de date
            Statement statement = DbConnection.connectToDb().createStatement();
            // contine interogarea SQL pentru a selecta toate datele din tabelul Utilizator
            String query = "SELECT * FROM Utilizator";
            // am executat interogarea SQL stocata in query si salvez rezultatele in obiectul de tip ResultSet (obiect care ne permite accesul la toate datele)
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String idStr = resultSet.getString("Id");
                UUID id = UUID.fromString(idStr);
                String nume = resultSet.getString("Nume");
                String prenume = resultSet.getString("Prenume");
                java.sql.Date sqlDate = resultSet.getDate("DataNasterii");
                LocalDate dataNasterii = sqlDate.toLocalDate();
                String gen = resultSet.getString("Gen");
                String adresa = resultSet.getString("Adresa");
                String email = resultSet.getString("Email");
                String parola = resultSet.getString("Parola");
                String telefon = resultSet.getString("Telefon");
                String cnp = resultSet.getString("Cnp");
                String seria = resultSet.getString("Seria");
                Utilizator utilizator = new Utilizator(id, nume, prenume, dataNasterii, gen, adresa, email, parola, telefon, cnp, seria);
                utilizatori.add(utilizator);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda selecteaza toti medicii din baza de date, apoi facem obiecte de tip Medic si stocam in array ul de medici
     */
    public void selectMediciDb() {
        try {
            Statement statement = DbConnection.connectToDb().createStatement();
            String query = "SELECT * FROM Medic";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String idStr = resultSet.getString("Id");
                UUID id = UUID.fromString(idStr);
                String nume = resultSet.getString("Nume");
                String prenume = resultSet.getString("Prenume");
                java.sql.Date sqlDate = resultSet.getDate("DataNasterii");
                LocalDate dataNasterii = sqlDate.toLocalDate();
                String gen = resultSet.getString("Gen");
                String adresa = resultSet.getString("Adresa");
                String email = resultSet.getString("Email");
                String parola = resultSet.getString("Parola");
                String telefon = resultSet.getString("Telefon");
                String cnp = resultSet.getString("Cnp");
                String seria = resultSet.getString("Seria");
                String facultate = resultSet.getString("Facultate");
                String specializare = resultSet.getString("Specializare");
                int aniExperienta = resultSet.getInt("AniExperienta");
                String programInceput = resultSet.getString("ProgramInceput");
                String programFinal = resultSet.getString("ProgramFinal");
                String descriere = resultSet.getString("Descriere");
                Medic medic = new Medic(id, nume, prenume, dataNasterii, gen, adresa, email, parola, telefon, cnp, seria, facultate, specializare, aniExperienta, programInceput, programFinal, descriere);
                medici.add(medic);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda selecteaza toate recenziile medicilor oferite de pacienti din baza de date
     */
    public void selectRecenziiDb() {
        try {
            Statement statement = DbConnection.connectToDb().createStatement();
            String query = "SELECT * FROM Recenzie";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String idStr = resultSet.getString("Id");
                UUID id = UUID.fromString(idStr);
                String idStrM = resultSet.getString("MedicId");
                UUID idMedic = UUID.fromString(idStrM);
                String idStrU = resultSet.getString("UtilizatorId");
                UUID idUtilizator = UUID.fromString(idStrU);
                String descriere = resultSet.getString("Descriere");
                int nota = resultSet.getInt("Nota");
                Timestamp timestamp = resultSet.getTimestamp("DataCreare");
                LocalDateTime dataCreare = timestamp.toLocalDateTime();
                Recenzie recenzie = new Recenzie(id, idMedic, idUtilizator, descriere, nota, dataCreare);
                recenzii.add(recenzie);

            }

//            for(Recenzie r : recenzii)
//                System.out.println(r);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda selecteaza toate programarile pacientilor din baza de date
     */
    public void selectProgramariDb() {
        try {
            Statement statement = DbConnection.connectToDb().createStatement();
            String query = "SELECT * FROM Programare ORDER BY OraInceput";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String idStr = resultSet.getString("Id");
                UUID id = UUID.fromString(idStr);
                String idStrM = resultSet.getString("MedicId");
                UUID idMedic = UUID.fromString(idStrM);
                String idStrU = resultSet.getString("UtilizatorId");
                UUID idUtilizator = UUID.fromString(idStrU);
                LocalDate dataCreare = resultSet.getDate("DataCreare").toLocalDate();
                String locatie = resultSet.getString("Locatie");
                String tip = resultSet.getString("Tip");
                String stare = resultSet.getString("Stare");
                String oraInceput = resultSet.getString("OraInceput");
                Programare programare = new Programare(id,idUtilizator, idMedic, dataCreare, locatie, tip, stare, oraInceput);
                programari.add(programare);
            }

//            for(Programare p : programari)
//                System.out.println(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metoda selecteaza istoricul pacientilor din baza de date
     */
    public void selectIstoricDb() {
        try {
            Statement statement = DbConnection.connectToDb().createStatement();
            String query = "SELECT * FROM Istoric";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String idStr = resultSet.getString("Id");
                UUID id = UUID.fromString(idStr);
                String idProgrStr = resultSet.getString("ProgramareId");
                UUID idProgramre = UUID.fromString(idProgrStr);
                Timestamp timestamp = resultSet.getTimestamp("UltimaModificare");
                LocalDateTime ultimaModificare = timestamp.toLocalDateTime();
                double cost = resultSet.getDouble("Cost");
                String simptome = resultSet.getString("Simptome");
                String diagnostic = resultSet.getString("Diagnostic");
                String rezultateAnalize = resultSet.getString("RezultateAnalize");
                String tratament = resultSet.getString("Tratament");

                Istoric istoric1 = new Istoric(id, idProgramre, ultimaModificare, cost, simptome, diagnostic, rezultateAnalize, tratament);
                istoric.add(istoric1);
            }
//            for(Istoric i : istoric)
//                System.out.println(i);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda adauga un pacient in baza de date
     * @param utilizator acest parametru este un obiect de tip Utilizator primit dupa ce s-au validat cu succes datele utilizatorului
     * @return true daca s-a adaugat cu succes in baza, false in caz contrat
     */
    public boolean adaugarePacient(Utilizator utilizator) {
        try (
                Statement statement = DbConnection.connectToDb().createStatement();) {

            // scriu manual interogarea sql pentru a adauga datele pacientului in tabelul Utilizator
            String query = "INSERT INTO Utilizator (Id, Nume, Prenume, DataNasterii, Gen, Adresa, Email, Parola, Telefon, Cnp, Seria) " +
                    "VALUES ('" + utilizator.getId() + "', '" +
                    utilizator.getNume() + "', '" +
                    utilizator.getPrenume() + "', '" +
                    utilizator.getDataNasterii() + "', '" +
                    utilizator.getGen() + "', '" +
                    utilizator.getAdresa() + "', '" +
                    utilizator.getEmail() + "', '" +
                    utilizator.getParola() + "', '" +
                    utilizator.getTelefon() + "', '" +
                    utilizator.getCnp() + "', '" +
                    utilizator.getSeria() + "')";

            // aici am executat interogarea
            int rowsInserted = statement.executeUpdate(query);
            // verific daca nr de randuri afectate > 0
            if (rowsInserted > 0)
            {
                // il adaug in array de utilizatori
                this.utilizatori.add(utilizator);
                //retin doar informatiile necesare a pacientului care si-a creat cont
                UtilizatorDto utilizatorDto = new UtilizatorDto(utilizator.getId(), utilizator.getEmail(), utilizator.getParola(), utilizator.getNume(), utilizator.getPrenume(), "", TipUtilizatorEnum.PACIENT);
                this.utilizatorLogat = utilizatorDto;
            }
            // am verif dacă adăugarea a avut succes
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda adauga un medic in baza de date
     * @param medic un obiect de tip Medic trimis ca si parametru dupa ce s-au validat cu succes datele
     * @return true in caz ca s-a adaugat cu succes in baza de date, altfel false
     */
    public boolean adaugareMedic(Medic medic) {
        try (
                Statement statement = DbConnection.connectToDb().createStatement();
        ) {

            String query = "INSERT INTO Medic (Id, Nume, Prenume, DataNasterii, Gen, Adresa, Email, Parola, Telefon, Cnp, Seria, Facultate, Specializare, AniExperienta, ProgramInceput, ProgramFinal, Descriere) " +
                    "VALUES ('" + medic.getId() + "', '" +
                    medic.getNume() + "', '" +
                    medic.getPrenume() + "', '" +
                    medic.getDataNasterii() + "', '" +
                    medic.getGen() + "', '" +
                    medic.getAdresa() + "', '" +
                    medic.getEmail() + "', '" +
                    medic.getParola() + "', '" +
                    medic.getTelefon() + "', '" +
                    medic.getCnp() + "', '" +
                    medic.getSeria() + "', '" +
                    medic.getFacultate() + "', '" +
                    medic.getSpecializare() + "', " +
                    medic.getAniExperienta() + ", '" +
                    medic.getProgramInceput() + "', '" +
                    medic.getProgramFinal() + "', '" +
                    medic.getDescriere() + "')";


            int rowsInserted = statement.executeUpdate(query);
            if (rowsInserted > 0)
            {
                this.medici.add(medic);

                UtilizatorDto utilizatorDto = new UtilizatorDto(medic.getId(), medic.getEmail(), medic.getParola(), medic.getNume(), medic.getPrenume(), medic.getSpecializare(), TipUtilizatorEnum.MEDIC);
                this.utilizatorLogat = utilizatorDto;
            }
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adauga un istoric in baza de date
     * @param istoric
     * @return
     */
    public boolean adaugareIstoric(Istoric istoric)
    {
        try(Statement statement = DbConnection.connectToDb().createStatement())
        {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            String query = "INSERT INTO Istoric (Id, ProgramareId, UltimaModificare, Cost, Simptome, Diagnostic, RezultateAnalize, Tratament) " +
                    "VALUES ('" + istoric.getId() + "', '" +
                    istoric.getIdProgramare() + "', '" +
                    formattedDateTime + "', '" +
                    istoric.getCost() + "', '" +
                    istoric.getSimptome() + "', '" +
                    istoric.getDiagnostic() + "', '" +
                    istoric.getRezultateAnalize() + "', '" +
                    istoric.getTratament() + "')";

            int randuriInserate = statement.executeUpdate(query);
            if(randuriInserate > 0)
            {
                this.istoric.add(istoric);
            }
            return randuriInserate > 0;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualizaeaza informatiile din istoric in baza de date
     * @param idIstoric
     * @param simptome
     * @param diagnostic
     * @param rezultateAnalize
     * @param tratament
     * @param cost
     * @return
     */
    public boolean actualizareIstoric(UUID idIstoric, String simptome, String diagnostic, String rezultateAnalize, String tratament, Double cost)
    {
        try(Statement statement = DbConnection.connectToDb().createStatement())
        {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            String query="UPDATE ISTORIC SET " +
                    "UltimaModificare = '" + formattedDateTime + "', " +
                    "Simptome = '" + simptome + "', " +
                    "Diagnostic = '" + diagnostic + "', " +
                    "RezultateAnalize = '" + rezultateAnalize + "', " +
                    "Tratament = '" + tratament + "', " +
                    "Cost = '" + cost + "' " +
                    "WHERE Id = '" + idIstoric + "'";
            int randuriInserate = statement.executeUpdate(query);

            if(randuriInserate > 0) {
                for (Istoric i : istoric) {
                    if (i.getId().equals(idIstoric)) {
                        i.setSimptome(simptome);
                        i.setDiagnostic(diagnostic);
                        i.setRezultateAnalize(rezultateAnalize);
                        i.setTratament(tratament);
                        i.setCost(cost);
                        i.setUltimaModificare(LocalDateTime.now());
                    }
                }
            }
            return randuriInserate > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualizeaza starea unei porgramari in baza de date
     * @param idProgr
     * @param stareProgramareSetataMedic
     * @return
     */
    public boolean actualizareStareProgr(UUID idProgr, String stareProgramareSetataMedic)
    {
        try(Statement statement = DbConnection.connectToDb().createStatement()){
            String query = "UPDATE Programare SET " +
                    "Stare = '" + stareProgramareSetataMedic + "' " +
                    "WHERE Id = '" + idProgr + "'";
            int randuriInserate = statement.executeUpdate(query);

            if (randuriInserate > 0)
            {
                for (Programare p : programari) {
                    if (p.getId().equals(idProgr)) {
                        p.setStare(stareProgramareSetataMedic);
                    }
                }
            }

            return randuriInserate > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Actualizeaza informatiile din profil a unui utilizator medic in baza de date
     * @param nume
     * @param prenume
     * @param adresa
     * @param email
     * @param telefon
     * @param descriere
     * @param programInceput
     * @param programFinal
     * @return
     */
    public boolean editareProfil(String nume, String prenume, String adresa, String email, String telefon, String descriere, String programInceput, String programFinal)
    {
        try(Statement statement = DbConnection.connectToDb().createStatement())
        {
            String query="UPDATE MEDIC SET " +
                    "Nume = '" + nume + "', " +
                    "Prenume = '" + prenume + "', " +
                    "Adresa = '" + adresa + "', " +
                    "Email= '" + email + "', " +
                    "Telefon = '" + telefon + "', " +
                    "Descriere = '" + descriere + "', " +
                    "ProgramInceput = '" + programInceput + "', " +
                    "ProgramFinal = '" + programFinal + "' " +
                    "WHERE Id = '" + utilizatorLogat.getId() + "'";
            int randuriInserate = statement.executeUpdate(query);

            if (randuriInserate > 0)
            {
                for (Medic medic : medici) {
                    if (medic.getId().equals(utilizatorLogat.getId())) {
                        medic.setNume(nume);
                        medic.setPrenume(prenume);
                        medic.setAdresa(adresa);
                        medic.setEmail(email);
                        medic.setTelefon(telefon);
                        medic.setDescriere(descriere);
                        medic.setProgramInceput(programInceput);
                        medic.setProgramFinal(programFinal);
                    }
                }
                utilizatorLogat.setEmail(email);
            }
            return randuriInserate > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adaugarea unei noi programari in baza de date
     * @param programare
     * @return
     */
    public boolean adaugareProgramare( Programare programare )
    {
        try(Statement statement = DbConnection.connectToDb().createStatement())
        {

            String query = "INSERT INTO Programare (Id, UtilizatorId, MedicId, DataCreare, Locatie, Tip, Stare, OraInceput) " +
                    "VALUES ('" + programare.getId() + "', '" +
                    programare.getIdUtilizator() + "', '" +
                    programare.getIdMedic() + "', '" +
                    programare.getDataCreare() + "', '" +
                    programare.getLocatie()+ "', '" +
                    programare.getTip()+ "', '" +
                    programare.getStare() + "', '" +
                    programare.getOraInceput() + "')";

            int randuriInserate = statement.executeUpdate(query);
            if(randuriInserate > 0)
            {
                this.programari.add(programare);
            }
            return randuriInserate > 0;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualizeaza informatiile din profil a unui utilizator pacient in baza de date
     * @param nume
     * @param prenume
     * @param adresa
     * @param email
     * @param telefon
     * @return
     */
    public boolean editareProfilPacient(String nume, String prenume, String adresa, String email, String telefon)
    {
        try(Statement statement = DbConnection.connectToDb().createStatement())
        {
            String query="UPDATE UTILIZATOR SET " +
                    "Nume = '" + nume + "', " +
                    "Prenume = '" + prenume + "', " +
                    "Adresa = '" + adresa + "', " +
                    "Email= '" + email + "', " +
                    "Telefon = '" + telefon + "' " +
                    "WHERE Id = '" + utilizatorLogat.getId() + "'";
            int randuriInserate = statement.executeUpdate(query);

            if (randuriInserate > 0)
            {
                for (Utilizator utilizator : utilizatori) {
                    if (utilizator.getId().equals(utilizatorLogat.getId())) {
                        utilizator.setNume(nume);
                        utilizator.setPrenume(prenume);
                        utilizator.setAdresa(adresa);
                        utilizator.setEmail(email);
                        utilizator.setTelefon(telefon);

                    }
                }
                utilizatorLogat.setEmail(email);
            }
            return randuriInserate > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adaugarea unei noi recenzii in baza de date
     * @param recenzie
     * @return
     */
    public boolean adaugareRecenzie (Recenzie recenzie)
    {
        try(Statement statement = DbConnection.connectToDb().createStatement())
        {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            String query = "INSERT INTO Recenzie (Id, MedicId, UtilizatorId, Descriere, Nota, DataCreare) " +
                    "VALUES ('" + recenzie.getId() + "', '" +
                    recenzie.getIdMedic() + "', '" +
                    recenzie.getIdUtilizator() + "', '" +
                    recenzie.getDescriere() + "', '" +
                    recenzie.getNota()+ "', '" +
                    formattedDateTime + "')";

            int randuriInserate = statement.executeUpdate(query);
            if(randuriInserate > 0)
            {
                this.recenzii.add(recenzie);
            }
            return randuriInserate > 0;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


