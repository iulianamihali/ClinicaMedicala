package controllers;

import enums.TipUtilizatorEnum;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.DbContext;
import servicii.InregistrareServiciu;

import java.io.IOException;

/**
 * Controller care se ocupa de logica asociata fisierului inregistrare.fxml
 */
public class InregistrareController {
    private DbContext dbContext;
    private InregistrareServiciu inregistrareServiciu;

    @FXML
    private Button butonInapoi;
    @FXML
    private ChoiceBox rolInregistrare;
    @FXML
    private TextField nume;
    @FXML
    private TextField prenume;
    @FXML
    private DatePicker dataNasterii;
    @FXML
    private TextField gen;
    @FXML
    private TextField adresa;
    @FXML
    private TextField email;
    @FXML
    private TextField parola;
    @FXML
    private TextField telefon;
    @FXML
    private TextField cnp;
    @FXML
    private TextField seria;
    @FXML
    private TextField facultate;
    @FXML
    private TextField specializare;
    @FXML
    private TextField aniExp;
    @FXML
    private TextField programInceput;
    @FXML
    private TextField programFinal;
    @FXML
    private TextField descriere;
    @FXML
    private Button butonTrimite;
    @FXML
    private Text textValidare;

    /**
     * Metoda care initializeaza variabla dbContext si creeaza obiectul de inregistrareServiciu
     * pentru a avea acces la metodele acestui controller care sunt implementate in aceea clasa
     * pentru a fi folosite atat de controllerul Inregistrare cat si de testele unitare
     * @param dbContext parametrul primit din controller ul de Autentificare atunci cand utilizatorul apasa pe butonul de "Inreigstreaza-te"
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext=dbContext;
        this.inregistrareServiciu = new InregistrareServiciu(dbContext);
    }

    /**
     * Metoda este apelata de JavaFx atunci cand fisierul FXML asociat este incarcat
     */
    public void initialize()
    {
        rolInregistrare.setItems(FXCollections.observableArrayList(TipUtilizatorEnum.values()));
        rolInregistrare.setValue(TipUtilizatorEnum.PACIENT);
        textValidare.setVisible(false);

    }

    /**
     * Metoda care se ocupa de butonul din interfata numit "Inapoi"
     * Incarca scena de autentificare
     */
    @FXML
    public void inapoi()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/autentificare.fxml"));
            Parent root = loader.load();

            AutentificareController controller = loader.getController();
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
     * Metoda care afiseaza campurile in functie de tipul utilizatorului
     */
    @FXML
    public void alegeRolInregistrare()
    {
        if (rolInregistrare.getValue() == TipUtilizatorEnum.PACIENT)
        {
            facultate.setVisible(false);
            specializare.setVisible(false);
            aniExp.setVisible(false);
            programInceput.setVisible(false);
            programFinal.setVisible(false);
            descriere.setVisible(false);
        }
        else
        {
            facultate.setVisible(true);
            specializare.setVisible(true);
            aniExp.setVisible(true);
            programInceput.setVisible(true);
            programFinal.setVisible(true);
            descriere.setVisible(true);
        }
    }

    /**
     * Metoda care se ocupa de butonul "Trimite" dupa ce utilizatorul a completat toate campurile
     * In functie de tipul utilizatorului se apeleaza functiile din inregistrareServiciu, pt pacient sau medic
     */
    public void trimiteDate()
    {
        if(rolInregistrare.getValue() == TipUtilizatorEnum.PACIENT)
        {
            if(inregistrareServiciu.validareCampuriPacient(nume.getText(), prenume.getText(), dataNasterii.getValue(), gen.getText(), adresa.getText(), email.getText(), parola.getText(), telefon.getText(), cnp.getText(), seria.getText()))
            {
                System.out.println("SUCCES VALIDARE PACIENT");
                textValidare.setVisible(false);
            }
            else
            {
                System.out.println("Esec validare pacient!");
                textValidare.setVisible(true);

            }
        }
        else
        {
            if(inregistrareServiciu.validareCampuriMedic(nume.getText(), prenume.getText(), dataNasterii.getValue(), gen.getText(), adresa.getText(), email.getText(), parola.getText(), telefon.getText(), cnp.getText(), seria.getText(), facultate.getText(), specializare.getText(), aniExp.getText(), programInceput.getText(), programFinal.getText(), descriere.getText()))
            {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardMedic.fxml"));
                    Parent root = loader.load();

                    DashboardMedicController controller = loader.getController();
                    controller.setareDbContext(dbContext);

                    Stage stage = (Stage)(butonTrimite).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            else
            {
                System.out.println("Esec validare medic!");
                textValidare.setVisible(true);

            }
        }
    }


}
