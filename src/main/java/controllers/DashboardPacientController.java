package controllers;

import dto.SesiuneLogareDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modele.DbContext;
import servicii.DashboardPacientServiciu;
import java.io.IOException;

/**
 * Controller-ul se ocupa cu afisarea meniului pentru pacient si redirectionarea catre scenele disponibile din butoanele de meniu
 */
public class DashboardPacientController {
    private DbContext dbContext;
    private DashboardPacientServiciu dashboardPacientServiciu;

    @FXML
    private Label meniuEmail;
    @FXML
    private Label dashboardTextBunVenit;
    @FXML
    private Label numeScena;
    @FXML
    private AnchorPane anchorScena;
    @FXML
    private Button butonDeconectarePacient;
    @FXML
    private Button butonIstoricPacient;
    @FXML
    private Button butonProfilPacient;
    @FXML
    private ImageView imgPacient;

    /**
     * Seteaza iconita pentru meniul de pacient
     */
    public void initialize()
    {
        Image img = new Image(getClass().getResourceAsStream("/img/patientProfile.png"));
        imgPacient.setImage(img);
    }

    /**
     * Seteaza dbContext siredirectioneaza catre panoul pacientului la initiealizarea
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        meniuEmail.setText(dbContext.utilizatorLogat.getEmail());
        dashboardTextBunVenit.setText(dbContext.utilizatorLogat.getPrenume());
        loadContent("/main/panouPacient.fxml");
        numeScena.setText("Panou");
    }

    /**
     * Redirectioneaza catrea optiunile disponibile din meniul pacientului in functie de care buton este apasat
     * @param fxmlFile
     */
    public void loadContent(String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newContent = loader.load();

            if ("/main/panouPacient.fxml".equals(fxmlFile))
            {
                PanouPacientController panouPacientController = loader.getController();
                panouPacientController.setareDbContext(dbContext);
                numeScena.setText("Panou");
            }
            else if ("/main/butonMediciInPacient.fxml".equals(fxmlFile))
            {
                ButonMediciInPacientiController butonMediciInPacientiController = loader.getController();
                butonMediciInPacientiController.setareDbContext(dbContext);
                numeScena.setText("Medici");
            }
            else if ("/main/istoricPacient.fxml".equals(fxmlFile))
            {
                IstoricPacientController istoricPacientController = loader.getController();
                istoricPacientController.setareDbContext(dbContext);
                numeScena.setText("Istoric");
            }
            else if ("/main/setariPacient.fxml".equals(fxmlFile))
            {
                SetariPacientController setariPacientController = loader.getController();
                setariPacientController.setareDbContext(dbContext);
                numeScena.setText("Editare profil");
            }

            anchorScena.getChildren().clear();
            anchorScena.getChildren().add(newContent);
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Eroare la incarcarea fișierului FXML: " + fxmlFile, e);
        }
    }

    /**
     * Incarca sceana de panou
     */
    @FXML
    public void butonPanou()
    {
        loadContent("/main/panouPacient.fxml");
    }

    /**
     * Incarca scena de medici
     */
    @FXML
    public void clickMedici()
    {
        loadContent("/main/butonMediciInPacient.fxml");
    }

    /**
     * Incarca scena de istoric
     */
    @FXML
    public void clickIstoric()
    {
        loadContent("/main/istoricPacient.fxml");
    }

    /**
     * Deconeacteaza utilizatorul logat si redirectioneaza catrea scnea de logarea si sterge sesiunea din fisier
     */
    @FXML
    public void clickDeconectare()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/autentificare.fxml"));
            Parent root = loader.load();

            AutentificareController controller = loader.getController();
            controller.setareDbContext(dbContext);

            Stage stage = (Stage)(butonDeconectarePacient).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SesiuneLogareDto.stergereFisierSesiune();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Incarca sceana de setari
     */
    @FXML
    public void clickSetari()
    {
        loadContent("/main/setariPacient.fxml");
    }

}
