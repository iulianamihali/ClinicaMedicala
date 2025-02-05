package controllers;

import dto.ProgramarePanouMedicDto;
import enums.StareProgramareEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modele.DbContext;
import modele.Medic;
import modele.Programare;
import servicii.DasboardMedicServiciu;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Controller-ul care afiseaza programarile pentru data curenta sau o data selectata de medic
 */
public class PanouMedicController {
    private DbContext dbContext;
    private DasboardMedicServiciu dasboardMedicServiciu;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<ProgramarePanouMedicDto> tabelMedic;
    @FXML
    private TableColumn<ProgramarePanouMedicDto, String> numePacient;
    @FXML
    private TableColumn<ProgramarePanouMedicDto, Integer> oraProgramarii;
    @FXML
    private TableColumn<ProgramarePanouMedicDto, String> tipProgr;
    @FXML
    private TableColumn<ProgramarePanouMedicDto, String> stareConsultatie;
    @FXML
    private Label textCount;
    @FXML
    private Label numeDoctor;
    @FXML
    private Label specializareDoctor;
    @FXML
    private ImageView imgDoctor;

    /**
     * Seteaza la initializare data curenta
     */
    @FXML
    public void initialize()
    {
        datePicker.setValue(LocalDate.now());
    }

    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date si seteaza iconita medicului in functie de genul
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);
        setareTabelMedic();
        textCount.setText(nrProgramariZi().toString());
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
        numeDoctor.setText("Dr. " + dbContext.utilizatorLogat.getNume() + " " + dbContext.utilizatorLogat.getPrenume());
        specializareDoctor.setText(dbContext.utilizatorLogat.getSpecializare());
        datePicker.setValue(LocalDate.now());

    }

    /**
     * Seteaza tabelul cu informatiile fiecarei programari si coloreaza in functie de starea programarii
     */
    public void setareTabelMedic()
    {
        numePacient.setCellValueFactory(new PropertyValueFactory<>("nume"));
        oraProgramarii.setCellValueFactory(new PropertyValueFactory<>("oraInceput"));
        tipProgr.setCellValueFactory(new PropertyValueFactory<>("tip"));
        stareConsultatie.setCellValueFactory(new PropertyValueFactory<>("stare"));

        List<ProgramarePanouMedicDto> listaProgrMedic = dasboardMedicServiciu.listaProgramariMedic(dbContext.utilizatorLogat.getId(), datePicker.getValue());
        ObservableList<ProgramarePanouMedicDto> observableList = FXCollections.observableArrayList(listaProgrMedic);
        tabelMedic.setItems(observableList);


        tabelMedic.setRowFactory(tv -> {
            TableRow<ProgramarePanouMedicDto> row = new TableRow<>(){

                @Override
                protected void updateItem(ProgramarePanouMedicDto programare, boolean empty) {
                    super.updateItem(programare, empty);

                    if (programare == null || empty) {
                        setStyle("");
                    } else {
                        if (programare.getStare() != null && programare.getStare().equals(StareProgramareEnum.FINALIZAT.toString())) {
                            setStyle("-fx-background-color:#AFE1AF;\n");
                        } else if (programare.getStare() != null && programare.getStare().equals(StareProgramareEnum.ANULAT.toString())){
                            setStyle("-fx-background-color: linear-gradient(to bottom right, #FFCDD2, #FF8A80);\n");
                        }
                        else if (programare.getStare() != null && programare.getStare().equals(StareProgramareEnum.CONFIRMAT.toString()))
                        {
                            setStyle("-fx-background-color: linear-gradient(to bottom right, #FFF9C4, #FFEB8D);\n");
                        }
                    }
                }
            };


            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                {
                    ProgramarePanouMedicDto programare = row.getItem();
                    if(programare != null) {
                        UUID idProgramare = programare.getIdProgramare();
                        System.out.println(idProgramare);
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/editareProgramareDeMedic.fxml"));
                            Parent root = loader.load();

                            EditareProgramareDeMedicController controller = loader.getController();
                            controller.setareDbContext(dbContext, idProgramare);

                            Stage stage = (Stage) (tabelMedic).getScene().getWindow();
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
     * Actiunea de a aleage data si a incarca programarile pe baza datei alese
     */
    public void alegeData()
    {
        setareTabelMedic();
        textCount.setText(nrProgramariZi().toString());

    }

    /**
     * Afiseaza numarul total de programari
     * @return
     */
    public Integer nrProgramariZi()
    {
        int cnt = 0;
        for(Programare p : dbContext.programari)
        {
            if(dbContext.utilizatorLogat.getId().equals(p.getIdMedic()) && datePicker.getValue().isEqual(p.getDataCreare()) && p.getStare() != null && p.getStare().toUpperCase().equals(StareProgramareEnum.CONFIRMAT.toString().toUpperCase()))
                cnt++;

        }
        return cnt;
    }

    /**
     * Determina genul medicului logat
     * @return
     */
    public String getGen()
    {
        String gen = null;
        for (Medic medic : dbContext.medici)
        {
            if (medic.getId().equals(dbContext.utilizatorLogat.getId()))
            {
                gen = medic.getGen();
                break;
            }
        }
        return gen;
    }
}
