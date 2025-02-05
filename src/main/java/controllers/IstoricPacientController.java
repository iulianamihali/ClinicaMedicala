package controllers;

import dto.IstoricPacientTabelDto;
import dto.IstoricTabelDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modele.DbContext;
import servicii.DashboardPacientServiciu;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Controller-ul se ocupa cu afisarea istoricului pentru pacienti
 **/
public class IstoricPacientController {
    private DbContext dbContext;
    private DashboardPacientServiciu dashboardPacientServiciu;

    @FXML
    private TableView<IstoricPacientTabelDto> tabelIstoricPacient;
    @FXML
    private TableColumn<IstoricPacientTabelDto, String> data;
    @FXML
    private TableColumn<IstoricPacientTabelDto, String> cost;
    @FXML
    private TableColumn<IstoricPacientTabelDto, String> medic;
    @FXML
    private TableColumn<IstoricPacientTabelDto, String> specialitate;

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        setareTabelIstoricPacient();
    }

    /**
     * Seteaza informatiile in tabel despre fiecare programare carea a fost finalizata
     */
    public void setareTabelIstoricPacient()
    {
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        medic.setCellValueFactory(new PropertyValueFactory<>("numeMedic"));
        specialitate.setCellValueFactory(new PropertyValueFactory<>("specialitate"));

        List<IstoricPacientTabelDto> lista = dashboardPacientServiciu.istoricPacientTabelDtoList();
        ObservableList<IstoricPacientTabelDto> observableList = FXCollections.observableArrayList(lista);
        tabelIstoricPacient.setItems(observableList);

        tabelIstoricPacient.setRowFactory(tv -> {
            TableRow<IstoricPacientTabelDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                {
                    IstoricPacientTabelDto programare = row.getItem(); // obtin obiectul asoicat randului
                    if(programare != null) {
                        UUID idProgramare = programare.getIdProgramare();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/pacientDetaliiProgramare.fxml"));
                            Parent root = loader.load();

                            PacientDetaliiProgramareController controller = loader.getController();
                            controller.setareDbContext(dbContext, idProgramare);
                            Stage stage = (Stage) (tabelIstoricPacient).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            return row;
        });

    }

}
