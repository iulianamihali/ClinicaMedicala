import dto.*;
import enums.StareProgramareEnum;
import enums.TipUtilizatorEnum;
import modele.DbContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import servicii.DasboardMedicServiciu;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MedicTest {

    @Test
    void testListaProgramari()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        DasboardMedicServiciu dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);

        List< ProgramarePanouMedicDto> lista = dasboardMedicServiciu.listaProgramariMedic(UUID.fromString("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5"), LocalDate.of(2025, 01, 13));
        Assertions.assertTrue( lista.size() > 0, "LISTA DE PROGRAMARI E GOALA!");
    }

    @Test
    void testDetaliiPacient()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5"), "alin.neagu@medicclinic.ro", "ParolaAlin2024", "Neagu", "Alinn", "Ortopedie", TipUtilizatorEnum.MEDIC);
        DasboardMedicServiciu dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);

        DetaliiPacientProgramareEditDto detaliiPacientProgramareEditDto = dasboardMedicServiciu.detaliiPacient(UUID.fromString("1D0C3F5F-0AF4-4665-92DC-0DD58217BC07"));
        Assertions.assertNotNull(detaliiPacientProgramareEditDto, "DETALIILE NU EXISTA!");
    }

    @Test
    void actualizareStareProgramare()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5"), "alin.neagu@medicclinic.ro", "ParolaAlin2024", "Neagu", "Alinn", "Ortopedie", TipUtilizatorEnum.MEDIC);
        DasboardMedicServiciu dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);

        boolean actualizare = dasboardMedicServiciu.actualizareStareProgr(UUID.fromString("1D0C3F5F-0AF4-4665-92DC-0DD58217BC07"), StareProgramareEnum.FINALIZAT.toString().toUpperCase());
        Assertions.assertTrue(actualizare, "STAREA NU A FOST ACTUALIZATA!");
    }

    @Test
    void listaPacientiMedic()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5"), "alin.neagu@medicclinic.ro", "ParolaAlin2024", "Neagu", "Alinn", "Ortopedie", TipUtilizatorEnum.MEDIC);
        DasboardMedicServiciu dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);

        List<TabelPacientiMedicDto> lista = dasboardMedicServiciu.listaPacientiMedic();
        Assertions.assertTrue(lista.size() > 0, "LISTA ESTE GOALA!");
    }

    @Test
    void totalProgramariPacient()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5"), "alin.neagu@medicclinic.ro", "ParolaAlin2024", "Neagu", "Alinn", "Ortopedie", TipUtilizatorEnum.MEDIC);
        DasboardMedicServiciu dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);

        int total = dasboardMedicServiciu.totalProgramariPacient(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"));
        Assertions.assertTrue(total > 0, "TOTALUL DE PROGRAMARI ESTE GOL!");
    }

    @Test
    void listaIstoric()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5"), "alin.neagu@medicclinic.ro", "ParolaAlin2024", "Neagu", "Alinn", "Ortopedie", TipUtilizatorEnum.MEDIC);
        DasboardMedicServiciu dasboardMedicServiciu = new DasboardMedicServiciu(dbContext);

        List<IstoricTabelDto> lista = dasboardMedicServiciu.listaIstoric();
        Assertions.assertTrue(lista.size() > 0, "LISTA ESTE GOALA DE ISTORIC!");
    }
}
