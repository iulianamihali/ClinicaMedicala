package modele;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Clasa este folosita pentru a face conexiunea la baza de date SQL
 * @author Mihali Iuliana-Calina
 */

public class DbConnection {
    private static final String URL = "jdbc:sqlserver://DESKTOP-2OSFLH9\\SQLEXPRESS;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";
    private static final String DATABASE_NAME = "clinica";

    /**
     * Creaza conexiune la baza de date daca nu exista creaza baza.
     *
     * @return o conexiune activa la baza de date
     * @throws SQLException daca ceva nu merge corect si apar erori de conectare
     */
    public static Connection connectToDb() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!existaBazaDeDate(connection)) {
                //creazaBazaDeDate(connection);
                //connection.setCatalog(DATABASE_NAME);
                //creazaTabelele(connection);
            } else {
                connection.setCatalog(DATABASE_NAME);
            }
        } catch (SQLException e) {
            System.out.println("Eroare conectare baza de date!");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * Verifica daca baza de date exista.
     */
    private static boolean existaBazaDeDate(Connection connection) throws SQLException {
        String checkDatabaseSQL = "SELECT name FROM sys.databases WHERE name = '" + DATABASE_NAME + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(checkDatabaseSQL)) {
            return resultSet.next();
        }
    }

    /**
     * Creare baza de date.
     */
    private static void creazaBazaDeDate(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE " + DATABASE_NAME;
        try (Statement statement = connection.createStatement()) {
            statement.execute(createDatabaseSQL);
            System.out.println("Baza de date " + DATABASE_NAME + " creata cu succes!");
        }
    }

    /**
     * Creaza tabelele daca nu esista in baza de date
     */
    private static void creazaTabelele(Connection connection) throws SQLException {
        String createTablesSQL = """
            IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Utilizator' AND xtype='U')
            CREATE TABLE Utilizator (
                Id UNIQUEIDENTIFIER PRIMARY KEY,
                Nume VARCHAR(256) NOT NULL,
                Prenume VARCHAR(256) NOT NULL,
                DataNasterii DATE NOT NULL,
                Gen VARCHAR(20) NOT NULL,
                Adresa VARCHAR(256) NOT NULL,
                Email VARCHAR(256) NOT NULL,
                Parola VARCHAR(256) NOT NULL,
                Telefon VARCHAR(50) NOT NULL,
                Cnp VARCHAR(13) NOT NULL,
                Seria VARCHAR(20) NOT NULL
            );

            IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Medic' AND xtype='U')
            CREATE TABLE Medic (
                Id UNIQUEIDENTIFIER PRIMARY KEY,
                Nume VARCHAR(256) NOT NULL,
                Prenume VARCHAR(256) NOT NULL,
                DataNasterii DATE NOT NULL,
                Gen VARCHAR(20) NOT NULL,
                Adresa VARCHAR(256) NOT NULL,
                Email VARCHAR(256) NOT NULL,
                Parola VARCHAR(256) NOT NULL,
                Telefon VARCHAR(50) NOT NULL,
                Cnp VARCHAR(13) NOT NULL,
                Seria VARCHAR(20) NOT NULL,
                Facultate VARCHAR(256) NOT NULL,
                Specializare VARCHAR(256) NOT NULL,
                AniExperienta INTEGER NOT NULL,
                ProgramInceput VARCHAR(10) NOT NULL,
                ProgramFinal VARCHAR(10) NOT NULL,
                Descriere VARCHAR(MAX) NULL
            );

            IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Recenzie' AND xtype='U')
            CREATE TABLE Recenzie (
                Id UNIQUEIDENTIFIER PRIMARY KEY,
                MedicId UNIQUEIDENTIFIER NULL,
                UtilizatorId UNIQUEIDENTIFIER NULL,
                Descriere VARCHAR(256) NULL,
                Nota INTEGER NOT NULL,
                DataCreare DATETIME NOT NULL
            );

            IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Programare' AND xtype='U')
            CREATE TABLE Programare (
                Id UNIQUEIDENTIFIER PRIMARY KEY,
                UtilizatorId UNIQUEIDENTIFIER,
                MedicId UNIQUEIDENTIFIER NULL,
                DataCreare DATE NOT NULL,
                Locatie VARCHAR(256) NOT NULL,
                Stare VARCHAR(15) NOT NULL,
                OraInceput VARCHAR(10) NULL
            );

            IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Istoric' AND xtype='U')
            CREATE TABLE Istoric (
                Id UNIQUEIDENTIFIER PRIMARY KEY,
                ProgramareId UNIQUEIDENTIFIER NULL,
                UltimaModificare DATETIME NOT NULL,
                Cost DECIMAL(18,0) NOT NULL,
                Simptome VARCHAR(MAX) NULL,
                Diagnostic VARCHAR(MAX) NULL,
                RezultateAnalize VARCHAR(MAX) NULL,
                Tratament VARCHAR(MAX) NULL
            );

            ALTER TABLE Recenzie ADD CONSTRAINT FK_Recenzie_Medic_Id FOREIGN KEY (MedicId) REFERENCES Medic(Id);
            ALTER TABLE Recenzie ADD CONSTRAINT FK_Recenzie_Utilizator_Id FOREIGN KEY (UtilizatorId) REFERENCES Utilizator(Id);
            ALTER TABLE Programare ADD CONSTRAINT FK_Programare_Utilizator_Id FOREIGN KEY (UtilizatorId) REFERENCES Utilizator(Id);
            ALTER TABLE Programare ADD CONSTRAINT FK_Programare_Medic_Id FOREIGN KEY (MedicId) REFERENCES Medic(Id);
            ALTER TABLE Istoric ADD CONSTRAINT FK_Istoric_Programare_Id FOREIGN KEY (ProgramareId) REFERENCES Programare(Id);
        """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTablesSQL);
            System.out.println("Tabelele au fost create cu succes!");
        }
    }
}
