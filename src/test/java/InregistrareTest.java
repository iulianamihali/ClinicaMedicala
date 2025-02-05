import modele.DbContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import servicii.InregistrareServiciu;

import java.time.LocalDate;

public class InregistrareTest {

    @Test
    void testInregistrarePacientSucces()
    {
        DbContext dbContext = new DbContext();
        InregistrareServiciu inregistrareServiciu = new InregistrareServiciu(dbContext);
        LocalDate dataNastere = LocalDate.of(2002,8,31);
        Assertions.assertEquals(true, inregistrareServiciu.validareCampuriPacient("Danci", "Mihai", dataNastere, "Masculin", "Timisoara", "danci.mihai@gmail.com", "12345", "0798123012", "5010515081234", "DE"),
                "Inregistrare cu pacient");
    }

    @Test
    void testInregistrarePacientEsec()
    {
        DbContext dbContext = new DbContext();
        InregistrareServiciu inregistrareServiciu = new InregistrareServiciu(dbContext);
        LocalDate dataNastere = LocalDate.of(2022,6,21);
        Assertions.assertEquals(false, inregistrareServiciu.validareCampuriPacient("Danci", "", dataNastere, "Masculin", "Timisoara", "danci.mihai@gmail.com", "12345", "0798123012", "5010515081234", "DE"),
                "Esec");
    }

    @Test
    void testInregistrareMedicSucces()
    {
        DbContext dbContext = new DbContext();
        InregistrareServiciu inregistrareServiciu = new InregistrareServiciu(dbContext);
        LocalDate dataNastere = LocalDate.of(2001,5,23);
        Assertions.assertEquals(true, inregistrareServiciu.validareCampuriMedic("Stetcu", "Ionela", dataNastere, "Feminin", "Borsa", "ionela.stetcu@e-uvt.ro", "123456789", "0798123012", "5010515081234", "DE", "Medicina", "Cardiologie", "10", "08:00", "16:00", "Specialist în tratamentul bolilor cardiovasculare"),
                "Inregistrare cu medic");
    }

    @Test
    void testInregistrareMedicEsec()
    {
        DbContext dbContext = new DbContext();
        InregistrareServiciu inregistrareServiciu = new InregistrareServiciu(dbContext);
        LocalDate dataNastere = LocalDate.of(1997,9,14);
        Assertions.assertEquals(false, inregistrareServiciu.validareCampuriMedic("", "Ionela", dataNastere, "Feminin", "Borsa", "ionela.stetcu@e-uvt.ro", "123456789", "0798123012", "5010515081234", "DE", "Medicină", "Cardiologie", "10", "08:00", "16:00", "Specialist în tratamentul bolilor cardiovasculare"),
                "Inregistrare cu esec pt medic");
    }

}
