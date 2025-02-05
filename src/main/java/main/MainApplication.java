package main;

import controllers.AutentificareController;
import controllers.DashboardMedicController;
import controllers.DashboardPacientController;
import dto.InformatiiSesiuneDto;
import dto.SesiuneLogareDto;
import dto.UtilizatorDto;
import enums.TipUtilizatorEnum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modele.DbContext;
import modele.Medic;
import modele.Utilizator;
import java.io.IOException;

/**
 * Clasa carea cotine main-ul aplicatiei
 */
public class MainApplication extends Application {
    private static Stage stagePrimar;

    /**
     * Creaza stage ul principal si incarca scena de autentificare
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        stagePrimar = stage;
        DbContext context = new DbContext();
        if (!logicaSesiune(context))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("autentificare.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 500);

            // trimitem dbcontext la controller
            AutentificareController controller = fxmlLoader.getController();
            controller.setareDbContext(context);
            stagePrimar.setScene(scene);

        }

        stagePrimar.setTitle("Clinica Sanatatea Ta");
        stagePrimar.show();
        stagePrimar.setResizable(false);
        stagePrimar.getIcons().add(new Image(getClass().getResourceAsStream("/img/logoStage.png")));



    }

    /**
     * Incarca scena de meniu daca in fisierul de sesiune se afla un id al unui utilizator care nu a fost deconectat inainte
     * altfel incarca scena de autentificare
     * @param dbContext
     * @return rezultatul de sesiune
     */
    public boolean logicaSesiune(DbContext dbContext)
    {
        InformatiiSesiuneDto obiect = SesiuneLogareDto.citireFisier();

        if (obiect != null)
        {
            if (obiect.getTip().equals(TipUtilizatorEnum.PACIENT))
            {
                for (Utilizator utilizator : dbContext.utilizatori)
                {
                    if (utilizator.getId().equals(obiect.getId()))
                    {
                        dbContext.utilizatorLogat =  new UtilizatorDto(obiect.getId(), utilizator.getEmail(), utilizator.getParola(), utilizator.getNume(), utilizator.getPrenume(), "", obiect.getTip());
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardPacient.fxml"));
                            Parent root = loader.load();

                            DashboardPacientController controller = loader.getController();
                            controller.setareDbContext(dbContext);

                            Stage stage = MainApplication.stagePrimar;
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        return true;
                    }
                }
            }
            else if (obiect.getTip().equals(TipUtilizatorEnum.MEDIC))
            {
                for (Medic medic : dbContext.medici)
                {
                    if (medic.getId().equals(obiect.getId()))
                    {
                        dbContext.utilizatorLogat =  new UtilizatorDto(obiect.getId(), medic.getEmail(), medic.getParola(), medic.getNume(), medic.getPrenume(), medic.getSpecializare(), obiect.getTip());
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/dashboardMedic.fxml"));
                            Parent root = loader.load();

                            DashboardMedicController controller = loader.getController();
                            controller.setareDbContext(dbContext);

                            Stage stage = MainApplication.stagePrimar;
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        return true;
                    }
                }
            }

        }

        return false;
    }

    public static void main(String[] args) {

        launch();
    }
}