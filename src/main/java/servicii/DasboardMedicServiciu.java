package servicii;

import dto.DetaliiPacientProgramareEditDto;
import dto.IstoricTabelDto;
import dto.ProgramarePanouMedicDto;
import dto.TabelPacientiMedicDto;
import enums.StareProgramareEnum;
import modele.DbContext;
import modele.Istoric;
import modele.Programare;
import modele.Utilizator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Clasa se ocupa cu toate oepratiile asupra datelor din baza din meniul de medic
 */
public class DasboardMedicServiciu {
    private DbContext dbContext;

    /**
     * Constructor
     * @param dbContext
     */
    public DasboardMedicServiciu(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    /**
     * Determina programarile unui medic pentru o data specificata
     * @param id medicului
     * @param data aleasa de medic
     * @return o lista de programari
     */
    public List<ProgramarePanouMedicDto> listaProgramariMedic(UUID id, LocalDate data)
    {
        List<ProgramarePanouMedicDto> lista = new ArrayList<>();
        for(Programare programare : dbContext.programari)
        {
            if(programare.getIdMedic().equals(id) && programare.getDataCreare().isEqual(data.atStartOfDay().toLocalDate()))
            {
                for(Utilizator u : dbContext.utilizatori)
                {
                    if(u.getId().equals(programare.getIdUtilizator()))
                    {
                        String numePrenume = u.getNume() + " " + u.getPrenume();
                        ProgramarePanouMedicDto progr = new ProgramarePanouMedicDto(numePrenume, programare.getOraInceput(), programare.getTip(), programare.getStare(), programare.getId());
                        lista.add(progr);
                        break;
                    }
                }
            }
        }

        return lista;
    }

    /**
     * Informatiile despre un pacient pe baza unei programari
     * @param idProgr id-ul unei programari
     * @return informatiile depre un pacient
     */
    public DetaliiPacientProgramareEditDto detaliiPacient(UUID idProgr)
    {
        UUID idPacient = null;
        for(Programare p : dbContext.programari)
        {
            if(p.getId().equals(idProgr))
            {
                idPacient = p.getIdUtilizator();
                break;
            }
        }

        for(Utilizator u : dbContext.utilizatori)
        {
            if(u.getId().equals(idPacient))
            {
                LocalDate dataCurenta = LocalDate.now();

                int varsta = Period.between(u.getDataNasterii(), dataCurenta).getYears();
                DetaliiPacientProgramareEditDto detaliiPacientProgramareEditDto = new DetaliiPacientProgramareEditDto(u.getNume(), u.getPrenume(), u.getGen(), varsta, u.getDataNasterii(), u.getEmail(), u.getTelefon(), u.getCnp(), u.getSeria());
                return detaliiPacientProgramareEditDto;
            }
        }
        return null;
    }

    /**
     * Salveaza informatiile asupra unei programari pentru a crea un istoric
     * @param simptome
     * @param diagnostic
     * @param rezultateAnalize
     * @param tratament
     * @param idProgramare
     * @param costT
     * @return
     */
    public boolean informatiiFisaMedicalaEdit(String simptome, String diagnostic, String rezultateAnalize, String tratament, UUID idProgramare, Double costT)
    {

        if ( (simptome != null && !simptome.isEmpty()) || (diagnostic != null && !diagnostic.isEmpty()) || (rezultateAnalize != null && !rezultateAnalize.isEmpty()) || (tratament != null && !tratament.isEmpty()))
        {
            UUID gasireIdProgr = null;
            for(Istoric i : dbContext.istoric)
            {
                if(!i.getIdProgramare().equals(idProgramare))
                {
                    gasireIdProgr = null;
                }
                else
                {
                    gasireIdProgr = i.getId();
                    break;
                }

            }
            if (gasireIdProgr == null) {
                UUID idIstoric = UUID.randomUUID();
                Istoric istoric = new Istoric(idIstoric, idProgramare, LocalDateTime.now(), costT, simptome, diagnostic, rezultateAnalize, tratament);
                return dbContext.adaugareIstoric(istoric);
            }
            else
            {
                return dbContext.actualizareIstoric(gasireIdProgr, simptome, diagnostic, rezultateAnalize, tratament, costT);
            }

        }
        else
        {
            System.out.println("Unul sau mai multe câmpuri sunt goale");
            return false;
        }
    }

    /**
     * Actualizeaza strea unei porgramari existente
     * @param idProgr
     * @param stareProgramareSetataMedic
     * @return true daca a actualizat sau false daca a aparut o eraoare
     */
    public boolean actualizareStareProgr(UUID idProgr, String stareProgramareSetataMedic)
    {
            return dbContext.actualizareStareProgr(idProgr, stareProgramareSetataMedic);
    }


    /**
     * Determina lista pacientilor pentru un medic, un pacient apare in lista daca a avut cel putin o programare la acel medic
     * @return lista de pacienti
     */
    public List<TabelPacientiMedicDto> listaPacientiMedic ()
    {
        UUID idMedic = dbContext.utilizatorLogat.getId();
        List<TabelPacientiMedicDto> lista = new ArrayList<>();
        for(Programare programare : dbContext.programari)
        {
            if (programare.getIdMedic().equals(idMedic))
            {
                for(Utilizator utilizator : dbContext.utilizatori)
                {
                    if (utilizator.getId().equals(programare.getIdUtilizator()))
                    {
                        String numePrenume = utilizator.getNume() + " " + utilizator.getPrenume();
                        TabelPacientiMedicDto pacient = new TabelPacientiMedicDto(utilizator.getId(), numePrenume, utilizator.getGen(), utilizator.getTelefon(), totalProgramariPacient(utilizator.getId()));
                        boolean verifId = false;
                        for (TabelPacientiMedicDto pacientMedic : lista)
                        {
                            if (utilizator.getId().equals(pacientMedic.getIdPacient()))
                            {
                                verifId = true;
                                break;
                            }
                        }
                        if (!verifId)
                            lista.add(pacient);
                    }
                }
            }
        }

        return lista;
    }

    /**
     * Numarul total de programari pentru un pacient
     * @param idPacient id-ul pacientului
     * @return numarul toatal de programari pentru acel pacient
     */
    public int totalProgramariPacient(UUID idPacient)
    {
        int totalProgr = 0;
        for (Programare programare : dbContext.programari)
        {
            if (programare.getIdMedic().equals(dbContext.utilizatorLogat.getId()) && programare.getIdUtilizator().equals(idPacient))
            {
                totalProgr++;
            }
        }
        return totalProgr;
    }

    /**
     * Determina lista de istoric pentru medicul logat
     * @return lista de istoric
     */
    public List<IstoricTabelDto> listaIstoric()
    {
        UUID idMedic = dbContext.utilizatorLogat.getId();
        List<IstoricTabelDto> lista = new ArrayList<>();

        for(Programare programare : dbContext.programari)
        {
            if(programare.getIdMedic().equals(idMedic) && programare.getStare() != null && programare.getStare().toUpperCase().equals(StareProgramareEnum.FINALIZAT.toString().toUpperCase()))
            {
                for(Istoric istoric : dbContext.istoric)
                {
                    if (programare.getId().equals(istoric.getIdProgramare()))
                    {
                        for(Utilizator utilizator : dbContext.utilizatori)
                        {
                            if(utilizator.getId().equals(programare.getIdUtilizator()))
                            {
                                IstoricTabelDto istoricTabelDto = new IstoricTabelDto(programare.getId(), utilizator.getNume(), utilizator.getPrenume(), utilizator.getGen(), utilizator.getTelefon(), programare.getDataCreare());
                                lista.add(istoricTabelDto);
                            }
                        }
                    }
                }
            }
        }

        return lista;
    }

    /**
     * Editeaza informatiile din profilul medicului
     * @param nume
     * @param prenume
     * @param adresa
     * @param email
     * @param telefon
     * @param descriere
     * @param programInceput
     * @param programFinal
     * @return valoarea true daca au fost modiicate si false daca a aparut ceva eroare
     */
    public boolean editareProfilBaza(String nume, String prenume, String adresa, String email, String telefon, String descriere, String programInceput, String programFinal)
    {
        return dbContext.editareProfil(nume, prenume, adresa, email, telefon, descriere, programInceput, programFinal);
    }
}
