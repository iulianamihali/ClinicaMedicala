package controllers;

import dto.NumeSiSpecialitateMedicDto;
import dto.TabelPacientiMedicDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import modele.DbContext;
import modele.Recenzie;
import servicii.DashboardPacientServiciu;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * Controller-il care se ocupa cu logica pentru afisarea medicilor pentru pacienti
 * @author Mihali Iuliana Calina
 */
public class ButonMediciInPacientiController {
    private DbContext dbContext;
    private DashboardPacientServiciu dashboardPacientServiciu;
    private ObservableList<NumeSiSpecialitateMedicDto> listaOriginala;
    private FilteredList<NumeSiSpecialitateMedicDto> listaFiltrata;

    @FXML
    private FlowPane flowPaneCarduri;
    @FXML
    private Label numeMedicCard;
    @FXML
    private TextField baraCautare;


    /**
     * Seteaza dbContext serviciul pentru a avea acces la datele din baza si functiile din serviciu
     * @param dbContext
     */
    public void setareDbContext(DbContext dbContext)
    {
        this.dbContext = dbContext;
        dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        adaugareCarduri();
        cautare();
        CreareProgramareController controller = new CreareProgramareController();
        controller.setareDbContext(dbContext);

    }

    /**
     * Adauga cardurile medicilor in interfata
     */
    public void adaugareCarduri()
    {
        List<String> infoMedici = dashboardPacientServiciu.infoMedici();
       for(int i = 0; i < infoMedici.size(); i += 3)
       {
           String nume = infoMedici.get(i);
           String specialitate = infoMedici.get(i+1);
           String idMedic = infoMedici.get(i+2);
           AnchorPane card = creareCardMedic(nume, specialitate, idMedic);
           flowPaneCarduri.getChildren().add(card);
       }
    }

    /**
     * Creaza cardul de medic si adauga informatiile in acesta
     * @param nume
     * @param specialitate
     * @param idMedic
     * @return
     */
    public AnchorPane creareCardMedic(String nume, String specialitate, String idMedic)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/CardMedic.fxml"));
        AnchorPane card = null;
        try{
            card = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CardMedicController cardMedicController = loader.getController();
        cardMedicController.setareDbContext(dbContext);
        cardMedicController.setIdMedic(idMedic);
        cardMedicController.setNumeMedicCard(nume);
        cardMedicController.setSpecialitateMedicCard(specialitate);
        cardMedicController.setTotalRecenzii(String.valueOf(dashboardPacientServiciu.totalRecenzii(idMedic)) + " " + "recenzii");
        cardMedicController.setNrRecenzii(String.valueOf(dashboardPacientServiciu.calculRecenii(idMedic)) + " " + "din 5");
        return card;
    }

    /**
     * Filtreaza medicii afisati in carduri pe baza textului introdus in bara de cautare
     */
    @FXML
    public void cautare() {
        listaOriginala = FXCollections.observableArrayList(dashboardPacientServiciu.numeSiSpecialitateMedicDtos());
        listaFiltrata = new FilteredList<>(listaOriginala, medic -> true); // Toți medicii sunt inițial vizibili

        baraCautare.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                listaFiltrata.setPredicate(medic -> true);
            } else {
                String textCautat = newValue.toLowerCase().replaceAll("[^a-z0-9]", "");
                listaFiltrata.setPredicate(medic -> {
                    String nume = medic.getNume() != null ? medic.getNume().toLowerCase().replaceAll("[^a-z0-9]", "") : "";
                    String specialitate = medic.getSpecialitate() != null ? medic.getSpecialitate().toLowerCase().replaceAll("[^a-z0-9]", "") : "";
                    return nume.contains(textCautat) || specialitate.contains(textCautat);
                });
            }
            actualizareFlowPaneCarduri();
        });

        actualizareFlowPaneCarduri();

    }


    /**
     * Actualizeaza container-ul care contine cardurile medicilor atat la cautare cat si la initiere
     */
    public void actualizareFlowPaneCarduri() {
        flowPaneCarduri.getChildren().clear();
        for (NumeSiSpecialitateMedicDto medic : listaFiltrata) {
            AnchorPane card = creareCardMedic(
                    medic.getNume(),
                    medic.getSpecialitate(),
                    medic.getIdMedic().toString()
            );
            flowPaneCarduri.getChildren().add(card);
        }
    }


}
