import enums.TipUtilizatorEnum;
import modele.DbContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import servicii.AutentificareServiciu;

public class AutentificareTest {

    @Test
    void testAutentificarePacientSucces() {

        DbContext dbContext = new DbContext();
        AutentificareServiciu autentificareServiciu = new AutentificareServiciu(dbContext);

        Assertions.assertEquals(true, autentificareServiciu.validareEmailParola("andrei.mihaila@email.com", "ParolaAndrei2024", TipUtilizatorEnum.PACIENT),
                "Autentificare cu pacient");
    }

    @Test
    void testAutentificarePacientEsecParola() {

        DbContext dbContext = new DbContext();
        AutentificareServiciu autentificareServiciu = new AutentificareServiciu(dbContext);

        Assertions.assertEquals(false, autentificareServiciu.validareEmailParola("andrei.mihaila@email.com", "ParolaAndrei20243424243", TipUtilizatorEnum.PACIENT),
                "Autentificare cu parola pacientului gresita");
    }

    @Test
    void testAutentificarePacientEsecEmail() {

        DbContext dbContext = new DbContext();
        AutentificareServiciu autentificareServiciu = new AutentificareServiciu(dbContext);

        Assertions.assertEquals(false, autentificareServiciu.validareEmailParola("andrei.mihaila@email.comdd", "ParolaAndrei2024", TipUtilizatorEnum.PACIENT),
                "Autentificare cu email pacientului gresita");
    }

    @Test
    void testAutentificareMedicSucces() {

        DbContext dbContext = new DbContext();
        AutentificareServiciu autentificareServiciu = new AutentificareServiciu(dbContext);

        Assertions.assertEquals(true, autentificareServiciu.validareEmailParola("alin.neagu@medicclinic.ro", "ParolaAlin2024", TipUtilizatorEnum.MEDIC),
                "Autentificare cu medic");
    }

    @Test
    void testAutentificareMedicEsecParola() {

        DbContext dbContext = new DbContext();
        AutentificareServiciu autentificareServiciu = new AutentificareServiciu(dbContext);

        Assertions.assertEquals(false, autentificareServiciu.validareEmailParola("alin.neagu@medicclinic.ro", "ParolaAlin202242434", TipUtilizatorEnum.MEDIC),
                "Autentificare cu parola gresita a medicului");
    }

    @Test
    void testAutentificareMedicEsecEmail() {

        DbContext dbContext = new DbContext();
        AutentificareServiciu autentificareServiciu = new AutentificareServiciu(dbContext);

        Assertions.assertEquals(false, autentificareServiciu.validareEmailParola("alin.neagu@medicclinic.rorere", "ParolaAlin2024", TipUtilizatorEnum.MEDIC),
                "Autentificare cu email gresita a medicului");
    }
}
