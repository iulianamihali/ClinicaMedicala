package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modele.DbContext;
import modele.Utilizator;
import servicii.DashboardPacientServiciu;

/**
 * Controller-ul se ocupa cu afisarea informatiilor din setarile profilului  unui pacient
 */
public class SetariPacientController {
    private DbContext dbContext;
    private DashboardPacientServiciu dashboardPacientServiciu;

    @FXML
    private TextField nume;
    @FXML
    private TextField prenume;
    @FXML
    private TextField adresa;
    @FXML
    private TextField email;
    @FXML
    private TextField telefon;
    @FXML
    private Label mesajSucces;

    /**
     * Seteaza afisarea ca fals a unui mesaj de notificare asupra salvarii datelor
     */
    public void initialize()
    {
        mesajSucces.setVisible(false);
    }

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        updateInfo();
    }

    /**
     * Salveaza modificarile asupra informatiilor din setari
     */
    @FXML
    public void clickSalvare()
    {
        boolean updateSucces = dashboardPacientServiciu.editareProfilPacient(nume.getText(), prenume.getText(), adresa.getText(), email.getText(), telefon.getText());
        if(updateSucces)
        {
            System.out.println("Succes profil!");
            mesajSucces.setVisible(true);
        }
        else
        {
            System.out.println("Eroare salvare date");
        }
    }

    /**
     * Afiseaza informatiile din baza de date in interfata
     */
    public void updateInfo()
    {
        for (Utilizator utilizator : dbContext.utilizatori)
        {
            if (utilizator.getId().equals(dbContext.utilizatorLogat.getId()))
            {
                nume.setText(utilizator.getNume());
                prenume.setText(utilizator.getPrenume());
                adresa.setText(utilizator.getAdresa());
                email.setText(utilizator.getEmail());
                telefon.setText(utilizator.getTelefon());
            }
        }
    }
}
