package servicii;

import dto.SesiuneLogareDto;
import dto.UtilizatorDto;
import enums.TipUtilizatorEnum;
import modele.DbContext;
import modele.Medic;
import modele.Utilizator;

/**
 * Clasa reprezinta metodele de la controller-ul Autentificare, fiind utilizata atat de controller cat si de testele unitare
 * @author Mihali Iuliana-Calina
 */
public class AutentificareServiciu {
    private DbContext dbContext;
    public AutentificareServiciu(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    /**
     * Metoda valideaza tipul de utilizator, email-ul si parola
     * @param email emailul utilizatorului care trebuie validat
     * @param parola parola utilizatorului care trebuie validata
     * @param tipUtilizatorEnum reprezinta tipul utilizatorului pentru care se face validarea
     * @return true daca validarea a avut succes, sau false in caz contrat
     */
    public boolean validareEmailParola(String email, String parola, TipUtilizatorEnum tipUtilizatorEnum)
    {
        if(tipUtilizatorEnum == TipUtilizatorEnum.PACIENT)
        {
            for(Utilizator u : dbContext.utilizatori)
            {
                if(email.equals(u.getEmail()) && parola.equals(u.getParola()))
                {
                    System.out.println("S U C C E S  PACIENT!");
                    System.out.println(u.getId());
                    UtilizatorDto utilizatorDto = new UtilizatorDto(u.getId(), u.getEmail(), u.getParola(), u.getNume(), u.getPrenume(), "", TipUtilizatorEnum.PACIENT);
                    dbContext.utilizatorLogat = utilizatorDto;
                    SesiuneLogareDto.salvareSesiune(u.getId(), tipUtilizatorEnum);
                    return true;
                }
            }
        }
        else
        {
            for(Medic u : dbContext.medici)
            {
                if(email.equals(u.getEmail()) && parola.equals(u.getParola()))
                {
                    System.out.println("S U C C E S MEDIC!");
                    System.out.println(u.getId());
                    UtilizatorDto utilizatorDto = new UtilizatorDto(u.getId(), u.getEmail(), u.getParola(), u.getNume(), u.getPrenume(), u.getSpecializare(), TipUtilizatorEnum.MEDIC);
                    dbContext.utilizatorLogat = utilizatorDto;
                    SesiuneLogareDto.salvareSesiune(u.getId(), tipUtilizatorEnum);
                    return true;
                }
            }
        }
        return false;
    }
}
