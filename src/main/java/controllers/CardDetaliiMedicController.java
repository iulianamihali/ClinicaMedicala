package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modele.DbContext;
import modele.Medic;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

/**
 * Controller-ul se ocupa cu afisarea informatiilor din fiecare card de medic si actiunile acestuia
 * @author Mihali Iuliana Calina
 */
public class CardDetaliiMedicController {
    private DbContext dbContext;
    private String idMedic;

    @FXML
    private Button butonInapoi;
    @FXML
    private Label numeMedic;
    @FXML
    private Label specialitate;
    @FXML
    private TextArea descriere;
    @FXML
    private Label aniExp;
    @FXML
    private Label programLucru;
    @FXML
    private Label facultate;
    @FXML
    private ImageView imgDoctor;


    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    /**
     * Seteaza id-ul medicului pentru card ca dupa sa putem avea acces la id
     * @param idMedic
     */
    public void setIdMedic(String idMedic)
    {
        this.idMedic = idMedic;
        info();
        String gen = getGen();
        if (gen.equals("Feminin"))
        {
            Image img = new Image(getClass().getResourceAsStream("/img/womanDoctor.png"));
            imgDoctor.setImage(img);

        }
        else
        {
            Image img = new Image(getClass().getResourceAsStream("/img/manDoctor.png"));
            imgDoctor.setImage(img);
        }

    }


    /**
     * Intoarce pacientul in dashboard
     */
    @FXML
    public void clickButonInapoi()
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
     * Seteaza informatiile cardului pe baza id-ului primit din exteriorul controller-ului
     */
    public void info()
    {
        for (Medic medic : dbContext.medici)
        {
            if (medic.getId().toString().equals(idMedic))
            {
                numeMedic.setText("Dr. " + medic.getNume() + " " + medic.getPrenume());
                specialitate.setText(medic.getSpecializare());
                descriere.setText(medic.getDescriere());
                aniExp.setText(String.valueOf(medic.getAniExperienta()));
                programLucru.setText(medic.getProgramInceput() + " - " + medic.getProgramFinal());
                facultate.setText(medic.getFacultate());

            }
        }
    }

    /**
     * Returneaza genul medicului pentru a putea afisa iconita corespunzatoare
     * @return gen medic
     */
    public String getGen()
    {
        String gen = null;
        for (Medic medic : dbContext.medici)
        {
            if (medic.getId().toString().equals(idMedic))
            {
                gen = medic.getGen();
                break;
            }
        }
        return gen;
    }
}
