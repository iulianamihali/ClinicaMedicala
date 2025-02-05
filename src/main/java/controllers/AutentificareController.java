package controllers;

import com.sun.tools.javac.Main;
import dto.InformatiiSesiuneDto;
import dto.SesiuneLogareDto;
import dto.UtilizatorDto;
import enums.TipUtilizatorEnum;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.MainApplication;
import modele.DbContext;
import modele.Medic;
import modele.Utilizator;
import servicii.AutentificareServiciu;

import java.io.IOException;

/**
 * Controller care se ocupa de logica asociata fiserului FXML
 * @author Mihali Iuliana-Calina
 */
public class AutentificareController {
    private DbContext dbContext;
    private AutentificareServiciu autentificareServiciu;

    @FXML
    private Button butonLogin;
    @FXML
    private ComboBox<TipUtilizatorEnum> comboList;

    @FXML
    private Button butonInregistrare;

    @FXML
    private TextField email;

    @FXML
    private TextField parola;
    @FXML
    private Text textEroare;

    /**
     * Metoda care initializeaza variabila dbContext
     * Metoda creeaza obiectul de tip AutentificareServiciu si ii trimite ca si parametru acel dbContext initializat
     * Avem nevoie de obiectul AutentificareServiciu pentru a folosi metodele care sunt legate de acest controller
     * @param dbContext este primit din Main si retine toate informatiile din baza de date in array-uri
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext=dbContext;
        this.autentificareServiciu = new AutentificareServiciu(dbContext);
    }

    /**
     * Metoda este apelata automat de JavaFx atunci cand fisierul FXML asociat este incarcat.
     * FXML defineste interfata grafica, iar controller-ul implementeaza logica pentru aceasta interfata.
     */
    @FXML
    public void initialize() {
        comboList.setItems(FXCollections.observableArrayList(TipUtilizatorEnum.values()));
        textEroare.setVisible(false);
        comboList.setValue(TipUtilizatorEnum.PACIENT);

    }

    /**
     * Metoda care schimba scenele, din scena de login -> in scena de inregistrare
     * Aceasta metoda se apeleaza cand utilizatorul apasa pe butonul de "Inregistreaza-te"
     * Butonul de inregistrare are evenimentul onAction setat cu numele acestei metode
     */
    @FXML
    public void inregistreazate()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/inregistrare.fxml"));
            Parent root = loader.load();

            InregistrareController controller = loader.getController();
            controller.setareDbContext(dbContext);
            // trimit dbcontext catre scena de inregistrare
            Stage stage = (Stage)(butonInregistrare).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Metoda care verifica daca validarea a avut succes, in caz contrat o sa apara in interfata grafica un mesaj de eroare
     */
    @FXML
    public void logare()
    {
        System.out.println(comboList.getValue());
        if(autentificareServiciu.validareEmailParola(email.getText(), parola.getText(), comboList.getValue())) {
            if(comboList.getValue() == TipUtilizatorEnum.MEDIC) {
                textEroare.setVisible(false);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardMedic.fxml"));
                    Parent root = loader.load();

                    DashboardMedicController controller = loader.getController();
                    controller.setareDbContext(dbContext);

                    Stage stage = (Stage) (butonLogin).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            else if(comboList.getValue() == TipUtilizatorEnum.PACIENT)
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardPacient.fxml"));
                    Parent root = loader.load();

                    DashboardPacientController controller = loader.getController();
                    controller.setareDbContext(dbContext);

                    Stage stage = (Stage) (butonLogin).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        else
        {
            textEroare.setVisible(true);
        }
    }



}