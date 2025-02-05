package controllers;

import dto.IstoricTabelDto;
import enums.StareProgramareEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modele.DbContext;
import servicii.DasboardMedicServiciu;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Controller-ul seteaza istoricul pentru un medic asupra tuturor programarilor pentru pacientii care au facut programare la acesta
 */
public class MedicIstoricController {
    private DbContext dbContext;
    private DasboardMedicServiciu dasboardMedicServiciu;

    @FXML
    private TableColumn<IstoricTabelDto, String> numePacient;
    @FXML
    private TableColumn<IstoricTabelDto, String> prenumePacient;
    @FXML
    private TableColumn<IstoricTabelDto, String> genPacient;
    @FXML
    private TableColumn<IstoricTabelDto, String> telefonPacient;
    @FXML
    private TableColumn<IstoricTabelDto, LocalDate> dataProgramarii;
    @FXML
    private TableView<IstoricTabelDto> tabelIstoricMedic;
    @FXML
    private LineChart <String, Number> istoricChart;


    public void initialize()
    {
    }

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);
        setareTabelIstoric();
        setLineChart();

    }

    /**
     * Seteaza tabelul de istoric cu informatiile despre fiecare programare care a fost finalizata
     */
    public void setareTabelIstoric()
    {
        numePacient.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumePacient.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        genPacient.setCellValueFactory(new PropertyValueFactory<>("gen"));
        telefonPacient.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        dataProgramarii.setCellValueFactory(new PropertyValueFactory<>("dataProgramarii"));

        List<IstoricTabelDto> lista = dasboardMedicServiciu.listaIstoric();
        lista.sort((dto1, dto2) -> dto2.getDataProgramarii().compareTo(dto1.getDataProgramarii()));
        ObservableList<IstoricTabelDto> observableList = FXCollections.observableArrayList(lista);
        observableList.reversed();
        tabelIstoricMedic.setItems(observableList);


        tabelIstoricMedic.setRowFactory(tv -> {
            TableRow<IstoricTabelDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                {
                    IstoricTabelDto programare = row.getItem(); // obtin obiectul asoicat randului
                    if(programare != null) {
                        UUID idProgramare = programare.getIdProgramare();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/editareProgramareDeMedic.fxml"));
                            Parent root = loader.load();

                            EditareProgramareDeMedicController controller = loader.getController();
                            controller.setareDbContext(dbContext, idProgramare);
                            Stage stage = (Stage) (tabelIstoricMedic).getScene().getWindow();
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

    /**
     * Seteaza informatiile pentru graficul din istoric pentru utlimele 7 zile
     */
    public void setLineChart()
    {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        LocalDate astazi = LocalDate.now();
        LocalDate inceputSaptamana = astazi.with(DayOfWeek.MONDAY);
        LocalDate sfarsitSaptamana = astazi.with(DayOfWeek.SUNDAY);

        int[] progrPeZi = new int[7];

        Arrays.fill(progrPeZi, 0);
        dbContext.programari.stream()
                .filter(p -> {
                    LocalDate data = p.getDataCreare();
                    return !data.isBefore(inceputSaptamana) && !data.isAfter(sfarsitSaptamana) && p.getIdMedic().equals(dbContext.utilizatorLogat.getId()) && p.getStare() != null && p.getStare().toUpperCase().equals(StareProgramareEnum.FINALIZAT.toString().toUpperCase());
                })
                .forEach(p -> {
                    int ziuaSaptamanii = p.getDataCreare().getDayOfWeek().getValue() - 1;
                    progrPeZi[ziuaSaptamanii]++;
                });
        String[] zileSaptamana = {"Luni", "Marți", "Miercuri", "Joi", "Vineri", "Sâmbătă", "Duminică"};
        for (int i = 0; i < 7; i++)
        {

            series.getData().add(new XYChart.Data<>(zileSaptamana[i], progrPeZi[i]));

        }
        istoricChart.getData().clear();
        series.setName("Vizualizare Activitate");
        istoricChart.getData().add(series);

        Node line = series.getNode();
        line.setStyle("-fx-stroke: linear-gradient(to bottom right, #188ba7, #306090);");
    }



}



