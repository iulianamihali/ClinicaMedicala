package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modele.DbContext;

import java.io.IOException;

/**
 * Controller-ul pentru un card de medic care este o componenta afisata pentru a putea sa te programezi la un anumit medic
 */
public class CardMedicController {
    private DbContext dbContext;
    private String idMedic;

    @FXML
    private Label numeMedicCard;
    @FXML
    private Label specialitateMedicCard;
    @FXML
    private Label totalRecenzii;
    @FXML
    private Label nrRecenzii;
    @FXML
    private Button butonSolicitaProgramare;
    @FXML
    private Button detaliiMedic;

    /**
     * Seteaza id-ul medicului
     * @param idMedic
     */
    public void setIdMedic(String idMedic)
    {
        this.idMedic = idMedic;
    }

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }


    /**
     * Seteaza numele medicului din card
     * @param numeMedic
     */
    @FXML
    public void setNumeMedicCard(String numeMedic)
    {
        numeMedicCard.setText(numeMedic);
    }

    /**
     * Seteaza specialitatea medicului din card
     * @param specialitateMedic
     */
    @FXML
    public void setSpecialitateMedicCard(String specialitateMedic)
    {
        specialitateMedicCard.setText(specialitateMedic);
    }

    /**
     * Seteaza numarul total de recenzii din card
     * @param total
     */
    @FXML
    public void setTotalRecenzii(String total)
    {
         totalRecenzii.setText(total);
    }

    /**
     * Seteaza nota pe baza recenziilor din card
     * @param nr
     */
    @FXML
    public void setNrRecenzii(String nr)
    {
        nrRecenzii.setText(nr);
    }

    /**
     * Redirectioneaza catre scena de programare
     */
    @FXML
    public void clickSolicitareProgramare()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/creareProgramare.fxml"));
            Parent root = loader.load();

            CreareProgramareController controller = loader.getController();
            controller.setareDbContext(dbContext);
            controller.setIdMedic(idMedic);
            Stage stage = (Stage) (butonSolicitaProgramare).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Redirectioneaza catre scena de detalii medic
     */
    @FXML
    public void clickDetaliiMedic()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/cardDetaliiMedic.fxml"));
            Parent root = loader.load();

            CardDetaliiMedicController controller = loader.getController();
            controller.setareDbContext(dbContext);
            controller.setIdMedic(idMedic);
            Stage stage = (Stage)(detaliiMedic).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
