package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modele.DbContext;
import java.io.IOException;

/**
 * Controller-ul se ocupa afisarea unui ghid de folosire a aplicatiei pe partea de pacient
 */
public class GhidPacientController {
    private DbContext dbContext;

    @FXML
    private Button butonInapoi;


    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    /**
     * Redirectioneaza pacientul inapoi la meniu
     */
    @FXML
    public void clickInapoi()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardPacient.fxml"));
            Parent root = loader.load();

            DashboardPacientController controller = loader.getController();
            controller.setareDbContext(dbContext);

            Stage stage = (Stage)(butonInapoi).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
