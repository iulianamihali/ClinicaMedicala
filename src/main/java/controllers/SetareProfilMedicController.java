package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import modele.DbContext;
import modele.Medic;
import servicii.DasboardMedicServiciu;
import javafx.scene.control.Button;;

/**
 * Controller-ul seteaza informatiile din setarile de profil a medicului
 */
public class SetareProfilMedicController {
    private DbContext dbContext;
    private DasboardMedicServiciu dasboardMedicServiciu;

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
    private TextArea descriere;
    @FXML
    private TextField programInceput;
    @FXML
    private TextField programFinal;
    @FXML
    private Button butonSalvare;
    @FXML
    private Label mesajSucces;

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);
        getInfo();
    }

    /**
     * Seteaza afisarea ca fals a unui mesaj de notificare asupra salvarii datelor
     */
    @FXML
    public void initialize()
    {
        mesajSucces.setVisible(false);
    }

    /**
     * Salveaza modificarile asupra informatiilor din setari
     */
    @FXML
    public void salveaza()
    {

        if(dasboardMedicServiciu.editareProfilBaza(nume.getText(), prenume.getText(), adresa.getText(), email.getText(), telefon.getText(), descriere.getText(), programInceput.getText(), programFinal.getText()))
        {
            System.out.println("Succes profil!");
            mesajSucces.setVisible(true);
        }
        else
        {
            System.out.println("Eroare");
        }
    }

    /**
     * Afiseaza informatiile din baza de date in interfata
     */
    public void getInfo()
    {
        for (Medic medic : dbContext.medici)
        {
            if (medic.getId().equals(dbContext.utilizatorLogat.getId())) {
                nume.setText(medic.getNume());
                prenume.setText(medic.getPrenume());
                adresa.setText(medic.getAdresa());
                email.setText(medic.getEmail());
                telefon.setText(medic.getTelefon());
                descriere.setText(medic.getDescriere());
                programInceput.setText(medic.getProgramInceput());
                programFinal.setText(medic.getProgramFinal());
            }
        }
    }
}

