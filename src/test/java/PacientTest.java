import dto.IstoricPacientTabelDto;
import dto.NumeSiSpecialitateMedicDto;
import dto.ProgramarePanouPacientDto;
import dto.UtilizatorDto;
import enums.StareProgramareEnum;
import enums.TipUtilizatorEnum;
import modele.DbContext;
import modele.Programare;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import servicii.DashboardPacientServiciu;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PacientTest {

    @Test
    void testListaProgramari()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        DashboardPacientServiciu dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        List<ProgramarePanouPacientDto> lista = dashboardPacientServiciu.listaProgramariPacient(LocalDate.of(2025, 01, 18));

        Assertions.assertTrue( lista.size() > 0, "LISTA DE PROGRAMARI E GOALA!");
    }

    @Test
    void testInfoMedici()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        DashboardPacientServiciu dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        List<String> lista = dashboardPacientServiciu.infoMedici();

        Assertions.assertTrue( lista.size() > 0, "LISTA DE MEDICI E GOALA!");
    }

    @Test
    void calculRecenzieMedic()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        DashboardPacientServiciu dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        double recenzie = dashboardPacientServiciu.calculRecenii("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5".toLowerCase());

        Assertions.assertTrue(recenzie > 0, "Recenzia trebuie să fie mai mare decât 0");
        Assertions.assertTrue(recenzie >= 1 && recenzie <= 5, "Recenzia trebuie să fie intre 1 și 5");
    }

    @Test
    void numeSiSpecialitateMedici()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        DashboardPacientServiciu dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        List<NumeSiSpecialitateMedicDto> lista = dashboardPacientServiciu.numeSiSpecialitateMedicDtos();

        Assertions.assertTrue( lista.size() > 0, "LISTA DE MEDICI E GOALA!");
    }

    @Test
    void oreMedicDisponibile()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        DashboardPacientServiciu dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        List<LocalTime> lista = dashboardPacientServiciu.oreDisp("084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5".toLowerCase(),LocalDate.of(2025, 1, 17));

        Assertions.assertTrue( lista.size() > 0, "LISTA DE ORE E GOALA!");
    }

    @Test
    void testCreareProgramare()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        int nrProgramariInainte = dbContext.programari.size();
        DashboardPacientServiciu dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        boolean adaugat = dashboardPacientServiciu.creareProgramare(UUID.randomUUID(), dbContext.utilizatorLogat.getId(), "084EEE5E-1A80-4AAC-A7E2-0DD20FE91ED5".toLowerCase(), LocalDate.now(), "Timisoara", "Control", StareProgramareEnum.CONFIRMAT,"10:00");
        int nrProgramariDupa = dbContext.programari.size();
        Assertions.assertEquals(true, adaugat, "NU A FOST ADAUGATA");
        Assertions.assertTrue( nrProgramariDupa > nrProgramariInainte, "NU A FOST ADAUGATA IN LISTA");
    }

    @Test
    void istoricPacient()
    {
        DbContext dbContext = new DbContext();
        dbContext.utilizatorLogat = new UtilizatorDto(UUID.fromString("5F4C53A9-5011-4327-82DB-FD346EE22890"), "mara.tudor@email.com", "ParolaMara2024", "Tudor", "Mara", "", TipUtilizatorEnum.PACIENT);
        DashboardPacientServiciu dashboardPacientServiciu = new DashboardPacientServiciu(dbContext);
        List<IstoricPacientTabelDto> lista = dashboardPacientServiciu.istoricPacientTabelDtoList();

        Assertions.assertTrue( lista.size() > 0, "LISTA DE ISTORIC E GOALA!");
    }
}
