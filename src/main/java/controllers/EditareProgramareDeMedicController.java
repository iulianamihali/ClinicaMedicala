package controllers;

import dto.DetaliiPacientProgramareEditDto;
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
import modele.Istoric;
import modele.Programare;
import servicii.DasboardMedicServiciu;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Controller-ul se ocupa cu editarea unei programari de medic
 */
public class EditareProgramareDeMedicController {
    private DbContext dbContext;
    private UUID idProgramare;
    private DasboardMedicServiciu dasboardMedicServiciu;

    @FXML
    private Label numePacient;
    @FXML
    private Label prenumePacient;
    @FXML
    private Label genPacient;
    @FXML
    private Label varstaPacient;
    @FXML
    private Label dataNPacient;
    @FXML
    private Label emailPacient;
    @FXML
    private Label telefonPacient;
    @FXML
    private Label cnpPacient;
    @FXML
    private Label seriaPacient;
    @FXML
    private Button butonAnulare;
    @FXML
    private Button butonSalvare;
    @FXML
    private TextArea simptomeField;
    @FXML
    private TextArea diagnosticField;
    @FXML
    private TextArea rezultateField;
    @FXML
    private TextArea tratamentField;
    @FXML
    private TextField cost;
    @FXML
    private Label ultimaModificare;
    @FXML
    private ComboBox<String> comboBoxStareProgramare;


    /**
     * Setarea listei de stari pentru o programare
     */
    public void initialize()
    {
        ObservableList<String> listaStari = FXCollections.observableArrayList();
        for(StareProgramareEnum s : StareProgramareEnum.values())
        {
            listaStari.add(s.name());
        }
        comboBoxStareProgramare.setItems(listaStari);

    }

    /**
     * Setarea dbContext pentru a avea acces la datele din baza si id-ul programarii
     * @param dbContext
     * @param idProgramare
     */
    public void setareDbContext(DbContext dbContext, UUID idProgramare)
    {
        this.dbContext = dbContext;
        this.idProgramare = idProgramare;
        this.dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);
        setareDetaliiPacient();
        getInfoActualizateIstoric();
        getStareProgramare();

    }

    /**
     * Setarea informatiilor despre pacient in scena
     */
    public void setareDetaliiPacient()
    {
        DetaliiPacientProgramareEditDto detaliiPacientProgramareEditDto = dasboardMedicServiciu.detaliiPacient(idProgramare);
        if (detaliiPacientProgramareEditDto != null)
        {
            numePacient.setText(detaliiPacientProgramareEditDto.getNume());
            prenumePacient.setText(detaliiPacientProgramareEditDto.getPrenume());
            genPacient.setText(detaliiPacientProgramareEditDto.getGen());
            varstaPacient.setText(Integer.toString(detaliiPacientProgramareEditDto.getVarsta()));
            dataNPacient.setText(detaliiPacientProgramareEditDto.getDataN().toString());
            emailPacient.setText(detaliiPacientProgramareEditDto.getEmail());
            telefonPacient.setText(detaliiPacientProgramareEditDto.getTelefon());
            cnpPacient.setText(detaliiPacientProgramareEditDto.getCnp());
            seriaPacient.setText(detaliiPacientProgramareEditDto.getSeria());
        }

    }

    /**
     * Actiunea pentru butonul de anulare a modificarilor unei programari
     */
    public void anulare()
    {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardMedic.fxml"));
            Parent root = loader.load();

            DashboardMedicController controller = loader.getController();
            controller.setareDbContext(dbContext);

            Stage stage = (Stage)(butonAnulare).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Actiunea de salvare a modficarilor unei programari
     */
    public void salvare()
    {
        String simptome = simptomeField.getText();
        String diagnostic = diagnosticField.getText();
        String rezultateAnalize = rezultateField.getText();
        String tratament = tratamentField.getText();
        String costCamp = cost.getText();
        String stareProgramareSetataMedic = comboBoxStareProgramare.getValue();
        Double costT = null;
        if(costCamp.isEmpty())
        {
            costT = 0.0;
        }
        else
        {
            try{
                costT = Double.parseDouble(costCamp);
            } catch (NumberFormatException e) {
                costT = 0.0;
            }
        }

        boolean rez = dasboardMedicServiciu.informatiiFisaMedicalaEdit(simptome, diagnostic, rezultateAnalize, tratament, idProgramare, costT);
        boolean actualizareStare = dasboardMedicServiciu.actualizareStareProgr(idProgramare, stareProgramareSetataMedic);

        if(rez || actualizareStare) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardMedic.fxml"));
                Parent root = loader.load();

                DashboardMedicController controller = loader.getController();
                controller.setareDbContext(dbContext);

                Stage stage = (Stage) (butonSalvare).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else
        {
            System.out.println("Eroare");
        }
    }

    /**
     * Seteaza data ultimei modificari impreuna cu informatiile legate de programare din baza de date
     */
    public void getInfoActualizateIstoric()
    {
        for(Istoric i : dbContext.istoric)
        {
            if(i.getIdProgramare().equals(idProgramare))
            {
                LocalDateTime now = i.getUltimaModificare();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                if(i.getSimptome().equals("null"))
                {
                    simptomeField.setText("");
                }
                else
                {
                    simptomeField.setText(i.getSimptome());

                }
                if(i.getDiagnostic().equals("null"))
                {
                    diagnosticField.setText("");
                }
                else
                {
                    diagnosticField.setText(i.getDiagnostic());

                }
                if(i.getRezultateAnalize().equals("null"))
                {
                    rezultateField.setText("");
                }
                else
                {
                    rezultateField.setText(i.getRezultateAnalize());

                }
                if(i.getTratament().equals("null"))
                {
                    tratamentField.setText("");
                }
                else
                {
                    tratamentField.setText(i.getTratament());

                }

                cost.setText(Double.toString(i.getCost()));
                if(i.getUltimaModificare().equals("null"))
                {
                    ultimaModificare.setText("");
                }
                else
                {
                    ultimaModificare.setText(formattedDateTime);

                }
            }
        }
    }

    /**
     * Seteza automat starea programarii din baza de date
     */
    public void getStareProgramare()
    {
        for(Programare p : dbContext.programari)
        {
            if (p.getId().equals(idProgramare))
            {
                comboBoxStareProgramare.setValue(p.getStare());
            }
        }
    }

}
