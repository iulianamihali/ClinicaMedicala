package controllers;


import dto.SesiuneLogareDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modele.DbContext;
import servicii.DasboardMedicServiciu;

import java.io.IOException;

/**
 * Controller-ul se ocupa cu afisarea meniului din stanga cu butoane a medicului si redirectionarea catre fiecare scena din meniu
 */
public class DashboardMedicController {
    private DbContext dbContext;
    private DasboardMedicServiciu dasboardMedicServiciu;

    @FXML
    private Button butonPanouMedic;
    @FXML
    private Button butonPacientiMedic;
    @FXML
    private Button butonIstoricMedic;
    @FXML
    private Button butonProfilMedic;
    @FXML
    private Button butonDeconectareMedic;
    @FXML
    private Label meniuEmail;
    @FXML
    private Label dashboardTextBunVenit;
    @FXML
    private AnchorPane anchorScena;
    @FXML
    private Label numeScena;
    @FXML
    private ImageView imgMedic;

    /**
     * Setarae dbContext pentru avea acces la datele din baza de date si redirectionarea automata catre panoul medicului
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        meniuEmail.setText(dbContext.utilizatorLogat.getEmail());
        dashboardTextBunVenit.setText(dbContext.utilizatorLogat.getPrenume());
        this.dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);
        loadContent("/main/panouMedic.fxml");
        numeScena.setText("Panou");
    }

    /**
     * Seteaza iconita de medic pentru meniu
     */
    @FXML
    public void initialize()
    {
        Image img = new Image(getClass().getResourceAsStream("/img/doctorIconDashboard.png"));
        imgMedic.setImage(img);
    }

    /**
     * Redirectioneaza catre scena de conectare si sterge utilizatorul logat si sterge sesiunea salvata in fisier
     */
    @FXML
    public void deconecteazate()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/autentificare.fxml"));
            Parent root = loader.load();

            AutentificareController controller = loader.getController();
            controller.setareDbContext(dbContext);

            Stage stage = (Stage)(butonDeconectareMedic).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SesiuneLogareDto.stergereFisierSesiune();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Redirectioneaza catre o scena disponibila pe baza optiunii la care un medic apasa din butoanele din meniu
     * @param fxmlFile
     */
    @FXML
    public void loadContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();

            if ("/main/panouMedic.fxml".equals(fxmlFile)) {
                PanouMedicController panouController = loader.getController();
                panouController.setareDbContext(dbContext);
                numeScena.setText("Panou");
            } else if ("/main/istoricMedic.fxml".equals(fxmlFile)) {
                MedicIstoricController istoricController = loader.getController();
                istoricController.setareDbContext(dbContext);
                numeScena.setText("Istoric");
            }
            else if ("/main/pacientiMedic.fxml".equals(fxmlFile)) {
                PacientiMedicController pacientiController = loader.getController();
                pacientiController.setareDbContext(dbContext);
                numeScena.setText("Pacienti");
            }
            else if ("/main/setareProfilMedic.fxml".equals(fxmlFile)) {
                SetareProfilMedicController setareProfilMedicController = loader.getController();
                setareProfilMedicController.setareDbContext(dbContext);
                numeScena.setText("Profil");
            }

            anchorScena.getChildren().clear();
            anchorScena.getChildren().add(newContent);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eroare la îincarcarea fișierului FXML: " + fxmlFile, e);
        }
    }

    /**
     * Incarca scena de istoric
     */
    @FXML
    public void istoricMedic()
    {
        loadContent("/main/istoricMedic.fxml");
    }

    /**
     * Incarca scena de pacienti
     */
    @FXML
    public void apasareButonPacienti()
    {
        loadContent("/main/pacientiMedic.fxml");
    }

    /**
     * Incarca scena de panou
     */
    @FXML
    public void apasarebutonPanou()
    {
         loadContent("/main/panouMedic.fxml");
    }

    /**
     * Incarca scena de profil
     */
    @FXML
    public void setareProfil()
    {
        loadContent("/main/setareProfilMedic.fxml");
    }


}
