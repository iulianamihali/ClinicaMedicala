package servicii;

import dto.IstoricPacientTabelDto;
import dto.NumeSiSpecialitateMedicDto;
import dto.ProgramarePanouPacientDto;
import enums.StareProgramareEnum;
import modele.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Clasa se ocupa cu toate oepratiile asupra datelor din baza din meniul de pacient
 */
public class DashboardPacientServiciu {
    private DbContext dbContext;

    /**
     * Constructor
     * @param dbContext
     */
    public DashboardPacientServiciu(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    /**
     * Determina lista de programari pentru pacient pentru o data anume
     * @param data
     * @return lista de programari
     */
    public List<ProgramarePanouPacientDto> listaProgramariPacient(LocalDate data)
    {
        List<ProgramarePanouPacientDto> lista = new ArrayList<>();

        for (Programare programare : dbContext.programari)
        {
            if (programare.getIdUtilizator().equals(dbContext.utilizatorLogat.getId()) && programare.getDataCreare().isEqual(data))
            {
                for(Medic medic : dbContext.medici)
                {
                    if(programare.getIdMedic().equals(medic.getId()))
                    {
                        ProgramarePanouPacientDto programarePanouPacientDto = new ProgramarePanouPacientDto(medic.getNume(), medic.getPrenume(), programare.getOraInceput(), programare.getLocatie(), medic.getSpecializare(), programare.getStare());
                        lista.add(programarePanouPacientDto);

                    }
                }

            }
        }
        return lista;
    }

    /**
     * Lista de medici pentru afisare in scopul solicitarii unei programari
     * @return lista de medici
     */
    public List<String> infoMedici()
    {
        List<String> info = new ArrayList<>();
        for(Medic medic : dbContext.medici)
        {
            String numePrenume = medic.getNume() + " " + medic.getPrenume();
            info.add(numePrenume);
            info.add(medic.getSpecializare());
            info.add(medic.getId().toString());
        }
        return info;
    }

    /**
     * Calculul notei de recenzie pentru un medic
     * @param idMedic
     * @return nota de recenzie
     */
    public double calculRecenii(String idMedic)
    {
        double suma = 0;
        int cnt = 0;

            for (Recenzie recenzie : dbContext.recenzii) {
                if (recenzie.getIdMedic().toString().equals(idMedic)) {
                    suma += recenzie.getNota();
                    cnt++;
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
     * Numarul total de recenzii pentru un medic
     * @param idMedic
     * @return total recenzii
     */
    public int totalRecenzii(String idMedic)
    {
        int cnt = 0;

            for (Recenzie recenzie : dbContext.recenzii) {
                if (recenzie.getIdMedic().toString().equals(idMedic)) {
                    cnt++;
                }

        }
        return cnt;
    }

    /**
     * Lista de medici cu nume si specialitate
     * @return lista informatii medici
     */
    public List<NumeSiSpecialitateMedicDto> numeSiSpecialitateMedicDtos()
    {
        List<NumeSiSpecialitateMedicDto> lista = new ArrayList<>();

        for(Medic medic : dbContext.medici)
        {
            String numeF = medic.getNume() + " " + medic.getPrenume();
            NumeSiSpecialitateMedicDto obiect = new NumeSiSpecialitateMedicDto(numeF, medic.getSpecializare(), medic.getId());
            lista.add(obiect);
        }

        return lista;
    }

    /**
     * Lista de ore disponibile pentru un medic pentru o anumita data
     * @param idMedic
     * @param dataAleasa
     * @return lista de ore disponibile
     */
    public List<LocalTime> oreDisp(String idMedic, LocalDate dataAleasa)
    {
        List<LocalTime> toateOrele = new ArrayList<>();
        LocalTime oraInceput = null, oraFinal = null;
        for(Medic medic : dbContext.medici)
        {
            if(medic.getId().toString().equals(idMedic))
            {
                oraInceput = LocalTime.parse(medic.getProgramInceput());
                oraFinal = LocalTime.parse(medic.getProgramFinal());
                break;
            }
        }

        while (oraInceput.isBefore(oraFinal))
        {
            toateOrele.add(oraInceput);
            oraInceput = oraInceput.plusMinutes(55);
        }

        List <LocalTime> oreDisponibile = new ArrayList<>(toateOrele);

        for(Programare programare : dbContext.programari)
        {

            if (programare.getIdMedic().toString().equals(idMedic) && programare.getDataCreare().isEqual(dataAleasa))
            {
                LocalTime oraProgramata = LocalTime.parse(programare.getOraInceput());

                oreDisponibile.removeIf(ora -> ora.equals(oraProgramata));

            }
        }


     return oreDisponibile;
    }

    /**
     * Creaza o programare pentru un anumit medic si pacientul logat
     * @param idProgramare
     * @param idPacient
     * @param idMedic
     * @param dataCreare
     * @param locatie
     * @param tip
     * @param stareProgramareEnum
     * @param oraInceput
     * @return valoarea treu daca s-a cerat programarea sau false daca a aparut ceva eroare
     */
    public boolean creareProgramare(UUID idProgramare, UUID idPacient, String idMedic, LocalDate dataCreare, String locatie, String tip, StareProgramareEnum stareProgramareEnum, String oraInceput)
    {
        Programare programare = new Programare(idProgramare, idPacient, UUID.fromString(idMedic), dataCreare, locatie, tip, stareProgramareEnum.toString(), oraInceput);
        return dbContext.adaugareProgramare(programare);

    }

    /**
     * Lisat cu istoricul medical pentru fiecare programare finalizata al pacientului logat
     * @return lsita istoric
     */
    public List<IstoricPacientTabelDto> istoricPacientTabelDtoList()
    {
        List <IstoricPacientTabelDto> lista = new ArrayList<>();

        for(Programare programare : dbContext.programari)
        {
            if (programare.getIdUtilizator().equals(dbContext.utilizatorLogat.getId()))
            {
                for(Istoric istoric : dbContext.istoric)
                {
                    if (istoric.getIdProgramare().equals(programare.getId()))
                    {
                        for (Medic medic : dbContext.medici)
                        {
                            if(programare.getIdMedic().equals(medic.getId()))
                            {
                                String numeComplet = medic.getNume() + " " + medic.getPrenume();
                                IstoricPacientTabelDto obiect = new IstoricPacientTabelDto(programare.getId(), programare.getDataCreare(), istoric.getCost(), numeComplet, medic.getSpecializare());
                                lista.add(obiect);

                            }
                        }
                    }
                }
            }
        }
        return lista;
    }

    /**
     * Editarea informatiilor din setarile profilului pacientului logat
     * @param nume
     * @param prenume
     * @param adresa
     * @param email
     * @param telefon
     * @return valoarea true daca au fost salvate modificarile sau false in caz contrar
     */
    public boolean editareProfilPacient(String nume, String prenume, String adresa, String email, String telefon)
    {
        return dbContext.editareProfilPacient(nume, prenume, adresa, email, telefon);
    }

    /**
     * Adauga o rencenzie pentru un anumit medic si utilizatorul logat, recenzia poate fi adaugata daca utilizatorul are minim un istoric la medicul curent
     * @param idProgramare
     * @param descriere
     * @param nota
     * @return valoarea true daca recenzia a fost adauagata sau false in caz contrar
     */
    public boolean adaugareRecenzie(UUID idProgramare, String descriere, int nota)
    {
        for(Programare programare : dbContext.programari)
        {
            if (programare.getId().equals(idProgramare))
            {
                Recenzie recenzie = new Recenzie(UUID.randomUUID(), programare.getIdMedic(), programare.getIdUtilizator(), descriere, nota, LocalDateTime.now());
                return dbContext.adaugareRecenzie(recenzie);
            }
        }
        return false;
    }


}
