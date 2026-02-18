package Servicio;


import jakarta.persistence.*;
import Entidades.Pista;
import Entidades.Reserva;
import Entidades.Socio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ClubService {

    private static EntityManagerFactory emf;

    public ClubService() {
        emf = Persistence.createEntityManagerFactory("club_damaPU");
    }



}
