package controllers;

import dto.TabelPacientiMedicDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.DbContext;
import modele.Recenzie;
import servicii.DasboardMedicServiciu;
import java.util.List;

/**
 * Controller-ul se ocupa cu afisarea pacientilor carea au avut cel putin o programare pentru un medic
 */
public class PacientiMedicController {
    private DbContext dbContext;
    private DasboardMedicServiciu dasboardMedicServiciu;
    private ObservableList<TabelPacientiMedicDto> listaOriginala;
    private FilteredList<TabelPacientiMedicDto> listaFiltrata;


    @FXML
    private TableView<TabelPacientiMedicDto> tabelPacienti;
    @FXML
    private TableColumn<TabelPacientiMedicDto, String> numePacient;
    @FXML
    private TableColumn<TabelPacientiMedicDto, String> genPacient;
    @FXML
    private TableColumn<TabelPacientiMedicDto, String> telefonPacient;
    @FXML
    private TableColumn<TabelPacientiMedicDto, Integer> totalProgramari;
    @FXML
    private TextField cautareField;
    @FXML
    private Label recenzii;
    @FXML
    private ImageView imgRecenzii5;
    @FXML
    private Label nrRecenzii;


    /**
     * Seteaza dbContext pentru a avea acces la datele din baza de date si seteaza cardul cu nota din recenzii a medicului
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        this.dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);
        setareTabelMedic();

        recenzii.setText(String.valueOf(calculRecenii()));
        Image img = new Image(getClass().getResourceAsStream("/img/stele_5.png"));
        imgRecenzii5.setImage(img);
        nrRecenzii.setText(String.valueOf(totalRecenzii()));

    }

    /**
     * Setarea informatiilor din tabel a pacientilor
     */
    public void setareTabelMedic()
    {
        numePacient.setCellValueFactory(new PropertyValueFactory<>("nume"));
        genPacient.setCellValueFactory(new PropertyValueFactory<>("gen"));
        telefonPacient.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        totalProgramari.setCellValueFactory(new PropertyValueFactory<>("totalProgramari"));

        List<TabelPacientiMedicDto> listaPacienti = dasboardMedicServiciu.listaPacientiMedic();
        ObservableList<TabelPacientiMedicDto> observableList = FXCollections.observableArrayList(listaPacienti);
        tabelPacienti.setItems(observableList);

        cautare();
    }

    /**
     * Filtreaza pacienti din tabel pe baza textului introdus in bara de cautare
     */
    public void cautare()
    {
        listaOriginala = FXCollections.observableArrayList(dasboardMedicServiciu.listaPacientiMedic());
        listaFiltrata = new FilteredList<>(listaOriginala, pacient -> true);

        tabelPacienti.setItems(listaFiltrata);

        cautareField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            listaFiltrata.setPredicate(pacient -> {
                if(newValue == null || newValue.isEmpty())
                {
                    return true;
                }
                String textCautat = newValue.toLowerCase();
                return pacient.getNume().toLowerCase().contains(textCautat) ||
                        pacient.getTelefon().toLowerCase().contains(textCautat) ||
                        pacient.getGen().toLowerCase().contains(textCautat) ||
                        String.valueOf(pacient.getTotalProgramari()).contains(textCautat);
            });
        }));
    }

    /**
     * Calculeaza nota pe baza recenziilor pentru medicul conectat
     * @return
     */
    public double calculRecenii()
    {
        double suma = 0;
        int cnt = 0;
        for (Recenzie recenzie : dbContext.recenzii)
        {
            if (recenzie.getIdMedic().equals(dbContext.utilizatorLogat.getId()))
            {
                suma += recenzie.getNota();
                cnt ++;
            }
        }
        if (suma > 0) {
            double total = suma / cnt;
            return total;
        }
        else {
            return 0;
        }
    }

    /**
     * Aduna numarul total de recenzii pentru medicul conectat
     * @return
     */
    public int totalRecenzii()
    {
        int cnt = 0;
        for( Recenzie recenzie : dbContext.recenzii )
        {
            if (recenzie.getIdMedic().equals(dbContext.utilizatorLogat.getId()))
            {
                cnt++;
            }
        }

        return cnt;
    }

}
