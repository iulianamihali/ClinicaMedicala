package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import modele.DbContext;
import modele.Istoric;
import modele.Programare;
import java.io.IOException;
import java.util.UUID;

/**
 * Controller-ul se ocupa cu afisarea detaliilor despre o preogramare
 */
public class PacientDetaliiProgramareController {
    private DbContext dbContext;
    private UUID idProgramare;

    @FXML
    private Label data;
    @FXML
    private TextArea simptome;
    @FXML
    private TextArea rezultateAnalize;
    @FXML
    private TextArea diagnostic;
    @FXML
    private TextArea tratament;
    @FXML
    private Label cost;
    @FXML
    private Label ultimaModificare;
    @FXML
    private Button butonInapoi;
    @FXML
    private Button butonRecenzie;

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext, UUID idProgramare)
    {
        this.dbContext = dbContext;
        this.idProgramare = idProgramare;
        setareInfo();
    }

    /**
     * Seteaza informatiile programarii din baza de date acestea incluzand toate detaliile scrise de catre medic
     */
    public void setareInfo()
    {
       for(Programare programare : dbContext.programari)
       {
           if(programare.getId().equals(idProgramare))
           {
               for( Istoric istoric : dbContext.istoric)
               {
                   if (istoric.getIdProgramare().equals(idProgramare))
                   {
                       data.setText(programare.getDataCreare().toString());
                       simptome.setText(istoric.getSimptome());
                       rezultateAnalize.setText(istoric.getRezultateAnalize());
                       diagnostic.setText(istoric.getDiagnostic());
                       tratament.setText(istoric.getTratament());
                       ultimaModificare.setText(istoric.getUltimaModificare().toLocalDate().toString());
                   }
               }
           }
       }
    }

    /**
     * Redirectioneaza pacientul catre meniu
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

    /**
     * Redirectioneaza pacientul catre scena de recenzie unde poate adauga o recenzie impreuna cu nota acesteia
     */
    @FXML
    public void clickRecenzie()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ScenaRecenzii.fxml"));
            Parent root = loader.load();

            ScenaRecenziiController controller = loader.getController();
            controller.setareDbContext(dbContext, idProgramare);

            Stage stage = (Stage)(butonRecenzie).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
