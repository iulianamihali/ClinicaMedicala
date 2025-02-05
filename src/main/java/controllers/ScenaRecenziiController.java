package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import modele.DbContext;
import servicii.DashboardPacientServiciu;
import java.io.IOException;
import java.util.UUID;

/**
 * Controler-ul care se ocupa cu crearea unei rezenzii pentru un medic
 */
public class ScenaRecenziiController {
    private DbContext dbContext;
    private UUID idProgramare;
    private DashboardPacientServiciu dashboardPacientServiciu;

    @FXML
    private TextArea parereTextArea;
    @FXML
    private ComboBox<Integer> comboNota;
    @FXML
    private Button butonAnulare;
    @FXML
    private Button butonSalvare;

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext, UUID idProgramare)
    {
        this.dbContext = dbContext;
        this.idProgramare = idProgramare;
        this.dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
    }

    /**
     * Seteaza lista la initializare cu note intre 1 si 5
     */
    @FXML
    public void initialize()
    {
        comboNota.getItems().addAll(1, 2, 3, 4, 5);
        comboNota.setValue(5);
    }

    /**
     * Salveaza o rencenzie in baza de date si redirectioneaza catre meniu
     */
    @FXML
    public void clickSalvare()
    {
        boolean confirmareAdaugareRecenzie = dashboardPacientServiciu.adaugareRecenzie(idProgramare, parereTextArea.getText(), comboNota.getValue());
        if(confirmareAdaugareRecenzie)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardPacient.fxml"));
                Parent root = loader.load();

                DashboardPacientController controller = loader.getController();
                controller.setareDbContext(dbContext);

                Stage stage = (Stage)(butonSalvare).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else
            System.out.println("Eroare adaugare recenzie!");

    }

    /**
     * Anuleaza crearea unei recenzii si redirectioneaza catre meniu
     */
    @FXML
    public void clickAnulare()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardPacient.fxml"));
            Parent root = loader.load();

            DashboardPacientController controller = loader.getController();
            controller.setareDbContext(dbContext);

            Stage stage = (Stage)(butonAnulare).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
