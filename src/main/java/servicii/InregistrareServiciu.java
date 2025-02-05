package servicii;

import modele.DbContext;
import modele.Medic;
import modele.Utilizator;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Clasa reprezinta metodele din controller-ul Inregistrare, este folosita atat in controller cat si in testele uniare
 * @author Mihali Iuliana-Calina
 */
public class InregistrareServiciu {
    private DbContext dbContext;
    public InregistrareServiciu(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    /**
     * Metoda valideaza datele pacientilor atunci cand se inregistreaza
     * @param nume numele pacientului care trebuie validat
     * @param prenume prenumele pacientului care trebuie validat
     * @param dataNasterii data de nastere a pacientului
     * @param gen genul pacientului -> feminin/masculin
     * @param adresa adresa pacientului
     * @param email email-ul pacientului
     * @param parola parola pacientului
     * @param telefon nr de telefon a pacientului
     * @param cnp codul numeric personal (CNP) a pacientului
     * @param seria seria documentului de identitate al pacientului
     * @return true daca toate datele sunt corecte, false daca ceva nu e valid
     */
    public boolean validareCampuriPacient(String nume, String prenume, LocalDate dataNasterii, String gen, String adresa, String email, String parola, String telefon, String cnp, String seria)
    {
        if (nume != null && !nume.isEmpty() &&
                prenume != null && !prenume.isEmpty() &&
                dataNasterii != null &&
                gen != null && !gen.isEmpty() &&
                adresa != null && !adresa.isEmpty() &&
                email != null && !email.isEmpty() &&
                parola != null && !parola.isEmpty() &&
                telefon != null && !telefon.isEmpty() &&
                cnp != null && !cnp.isEmpty() &&
                seria != null && !seria.isEmpty())
        {
                UUID id = UUID.randomUUID();
                Utilizator utilizator = new Utilizator(id, nume, prenume, dataNasterii, gen, adresa, email, parola, telefon, cnp, seria);
                return dbContext.adaugarePacient(utilizator);
        } else {
            System.out.println("Unul sau mai multe câmpuri sunt invalide!");
            return false;
        }
    }

    /**
     * Metoda care valideaza datele medicilor atunci cand se inregistreaza
     * @param nume numele medicului care trebuie validat
     * @param prenume prenumele medicului care trebuie validat
     * @param dataNasterii data de naștere a medicului
     * @param gen genul medicului (masculin, feminin)
     * @param adresa adresa medicului care trebuie validată
     * @param email email-ul medicului care trebuie verificat
     * @param parola parola medicului care trebuie validata
     * @param telefon numărul de telefon al medicului
     * @param cnp codul numeric personal (CNP) al medicului
     * @param seria seria documentului de identitate al medicului
     * @param facultate numele facultății absolvite de medic
     * @param specializare specializarea medicului
     * @param aniExp numărul de ani de experiență ai medicului
     * @param programInceput ora de început a programului de lucru
     * @param programFinal ora de sfârșit a programului de lucru
     * @param descriere o scurtă descriere despre medic
     * @return true dacă toate datele sunt valide, false dacă ceva este greșit
     */
    public boolean validareCampuriMedic(String nume, String prenume, LocalDate dataNasterii, String gen, String adresa, String email, String parola, String telefon, String cnp, String seria, String facultate, String specializare, String aniExp, String programInceput, String programFinal, String descriere)
    {
        if (nume != null && !nume.isEmpty() &&
                prenume != null && !prenume.isEmpty() &&
                dataNasterii != null &&
                gen != null && !gen.isEmpty() &&
                adresa != null && !adresa.isEmpty() &&
                email != null && !email.isEmpty() &&
                parola != null && !parola.isEmpty() &&
                telefon != null && !telefon.isEmpty() &&
                cnp != null && !cnp.isEmpty() &&
                seria != null && !seria.isEmpty() &&
                facultate != null && !facultate.isEmpty() &&
                specializare != null && !specializare.isEmpty() &&
                aniExp != null && !aniExp.isEmpty() &&
                programInceput != null && !programInceput.isEmpty() &&
                programFinal != null && !programFinal.isEmpty() &&
                descriere != null && !descriere.isEmpty())
        {
            UUID id = UUID.randomUUID();
            Medic medic = new Medic(id, nume, prenume, dataNasterii, gen, adresa, email, parola, telefon, cnp, seria, facultate, specializare, Integer.parseInt(aniExp), programInceput, programFinal, descriere);
            return dbContext.adaugareMedic(medic);
        } else {
            System.out.println("Unul sau mai multe câmpuri sunt invalide!");
            return false;
        }
    }


}
