package controllers;

import enums.StareProgramareEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modele.DbContext;
import modele.Programare;
import servicii.DashboardPacientServiciu;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

/**
 * Controller-ul se ocupa cu logica de creare a unei programari de catre pacient
 */
public class CreareProgramareController {
    private DbContext dbContext;
    private String idMedic;
    private DashboardPacientServiciu dashboardPacientServiciu;

    @FXML
    private ComboBox<LocalTime> oreDisponibile;
    @FXML
    private DatePicker data;
    @FXML
    private TextArea descriere;
    @FXML
    private Label locatie;
    @FXML
    private Button butonAnulare;
    @FXML
    private Button butonProgramare;


    /**
     * Inainte de afisarea scenei aceasta metoda dezactiveaza toate datele inaintea datei curente
     */
    @FXML
    public void initialize()
    {
        data.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);

    }

    /**
     * Seteaza id-ul medicului pentru a putea sti catre care medic se realizeaza programarea
     * @param idMedic
     */
    public void setIdMedic(String idMedic)
    {
        this.idMedic = idMedic;

    }

    /**
     * Setarea componentei din interfata grafica cu ore disponibile
     * @param dataAleasa
     */
    public void setareCombo(LocalDate dataAleasa)
    {
        ObservableList<LocalTime> elemente = FXCollections.observableArrayList(dashboardPacientServiciu.oreDisp(idMedic, dataAleasa));
        oreDisponibile.setItems(elemente);
    }

    /**
     * Permite alegerea datei din interfata
     */
    @FXML
    public void alegeData()
    {
        setareCombo(data.getValue());

    }

    /**
     * Creaza programarea si redirectioneaza catre meniu
     */
    @FXML
    public void clickProgrameaza()
    {
        boolean verificareAdaugare = dashboardPacientServiciu.creareProgramare(UUID.randomUUID(), dbContext.utilizatorLogat.getId(), idMedic, data.getValue(), locatie.getText(), descriere.getText(), StareProgramareEnum.CONFIRMAT, oreDisponibile.getValue().toString());
        if (verificareAdaugare)
        {
            setareCombo(data.getValue());
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardPacient.fxml"));
            Parent root = loader.load();

            DashboardPacientController controller = loader.getController();
            controller.setareDbContext(dbContext);

            Stage stage = (Stage)(butonProgramare).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Anuleaza crearea unei programari si redirectioneaza catre meniu
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
