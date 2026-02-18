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

    // Métodos para cargar todos los datos en el DashBoard

    /**
     * Método que carga los datos de todos los ComboBox
     * @Author Hugo
     */
    public void cargarDatosDashboard() {
        cargarSociosDashBoard();
        cargarPistasDashBoard();
        cargarReservasDashBoard();
    }

    /**
     * Método que carga los socios en el DashBoard
     * @return Lista de socios
     * @Author Hugo
     */
    public static List<Socio> cargarSociosDashBoard() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select s from Socio s");
        List<Socio> socios = q.getResultList();

        em.close();
        return socios;
    }

    /**
     * Método que carga las pistas en el DashBoard
     * @return Lista de pistas
     * @Author Hugo
     */
    public static List<Pista> cargarPistasDashBoard() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select p from Pista p");
        List<Pista> pistas = q.getResultList();

        em.close();
        return pistas;
    }

    /**
     * Método que carga las reservas en el DashBoard
     * @return Lista de reservas
     * @Author Hugo
     */
    public static List<Reserva> cargarReservasDashBoard() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select r from Reserva r");
        List<Reserva> reservas = q.getResultList();

        em.close();
        return reservas;
    }

}
