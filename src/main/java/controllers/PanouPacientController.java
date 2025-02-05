package controllers;

import dto.ProgramarePanouPacientDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.DbContext;
import servicii.DashboardPacientServiciu;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;


/**
 * Controler-ul afiseaza programarile curente pentru un pacient impreuna cu ghidul de utilizare
 */
public class PanouPacientController {
    private DbContext dbContext;
    private DashboardPacientServiciu dashboardPacientServiciu;

    @FXML
    private TableView<ProgramarePanouPacientDto> tabelPacient;
    @FXML
    private TableColumn<ProgramarePanouPacientDto, String> numeMedic;
    @FXML
    private TableColumn<ProgramarePanouPacientDto, String> oraProgramarii;
    @FXML
    private TableColumn<ProgramarePanouPacientDto, String> locatie;
    @FXML
    private TableColumn<ProgramarePanouPacientDto, String> specialitate;
    @FXML
    private TableColumn<ProgramarePanouPacientDto, String> stare;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label data;
    @FXML
    private Button butonGhid;

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        setareTabel();
    }

    /**
     * Seteaza data curenta pentru a afisa programarile
     */
    public void initialize()
    {
        datePicker.setValue(LocalDate.now());
        data.setText(formatareData(datePicker.getValue()));
    }

    /**
     * Seteaza tabelul cu informatiile despre fiecare progrmare
     */
    public void setareTabel()
    {
        numeMedic.setCellValueFactory(cellData -> {
            ProgramarePanouPacientDto programare = cellData.getValue();
            String numePrenume = programare.getNume() + " " + programare.getPrenume();
            return new SimpleStringProperty(numePrenume);

        });

        oraProgramarii.setCellValueFactory(new PropertyValueFactory<>("ora"));
        locatie.setCellValueFactory(new PropertyValueFactory<>("locatie"));
        specialitate.setCellValueFactory(new PropertyValueFactory<>("specialitate"));
        stare.setCellValueFactory(new PropertyValueFactory<>("stare"));

        locatie.setCellFactory(column -> {
            return new TableCell<ProgramarePanouPacientDto, String>() {
                private final Text text = new Text();

                {
                    text.wrappingWidthProperty().bind(locatie.widthProperty().subtract(10));
                    setGraphic(text);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText(null);
                    } else {
                        text.setText(item);
                    }
                }
            };
        });

        List<ProgramarePanouPacientDto> lista =  dashboardPacientServiciu.listaProgramariPacient(datePicker.getValue());
        ObservableList<ProgramarePanouPacientDto> observableList = FXCollections.observableArrayList(lista);
        tabelPacient.setItems(observableList);
    }

    /**
     * Schimba data pentru a filtra programarile din alta data
     */
    public void alegeData()
    {
        setareTabel();
        data.setText(formatareData(datePicker.getValue()));
    }

    /**
     * Formataeza data pentru afisare
     * @param date
     * @return
     */
    public String formatareData(LocalDate date)
    {
        int day = date.getDayOfMonth();
        String month = date.getMonth().getDisplayName(TextStyle.FULL, new Locale("ro"));
        int year = date.getYear();

        return day + " " + month + " " + year;
    }

    /**
     * Redirectioneaza catre scena de ghid de utilizare
     */
    @FXML
    public void clickGhid()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/ghidPacient.fxml"));
            Parent root = loader.load();

            GhidPacientController controller = loader.getController();
            controller.setareDbContext(dbContext);

            Stage stage = (Stage)(butonGhid).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
