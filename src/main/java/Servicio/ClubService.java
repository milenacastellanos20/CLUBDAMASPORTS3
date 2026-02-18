package Servicio;


import jakarta.persistence.*;
import Entidades.Pista;
import Entidades.Reserva;
import Entidades.Socio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    //Métodos que gestionan los socios

    /**
     * Método que inserta los socios en la BD
     * @param s --> Objeto de tipo Socio
     * @return diferentes cadenas de texto para validar excepciones o éxito de la operación
     * @Author Hugo
     */
    public String insertarSocio(Socio s) throws PersistenceException {

        EntityManager em = emf.createEntityManager();

        // Validaciones básicas
        if (s.getIdSocio().trim().isEmpty()) {
            em.close();
            return "ID vacío";
        }

        if (s.getDni().trim().isEmpty()) {
            em.close();
            return "DNI vacío";
        }

        if (s.getEmail().trim().isEmpty()) {
            em.close();
            return "EMAIL vacío";
        }

        // Comprobar ID repetido
        if (em.find(Socio.class, s.getIdSocio()) != null) {
            em.close();
            return "Socio ya existente";
        }

        // Comprobar DNI repetido
        List<Socio> dniList = em.createQuery(
                        "SELECT s FROM Socio s WHERE s.dni = :dni", Socio.class)
                .setParameter("dni", s.getDni())
                .getResultList();

        if (!dniList.isEmpty()) {
            em.close();
            return "DNI repetido";
        }

        // Comprobar email repetido
        List<Socio> emailList = em.createQuery(
                        "SELECT s FROM Socio s WHERE s.email = :email", Socio.class)
                .setParameter("email", s.getEmail())
                .getResultList();

        if (!emailList.isEmpty()) {
            em.close();
            return "EMAIL repetido";
        }

        // Insertar
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        em.close();

        return "inserción validada";
    }


    /**
     * Método carga todos los ID de socios en el ComboBox de la ventana de baja para seleccionarlos
     * @return lista de ID
     * @Author Hugo
     */
    public List<String> cargarSociosComboBox() {

        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select s.id from Socio s");
        List<String> idSocios = q.getResultList();

        em.close();
        return idSocios;
    }


    /**
     * Método que da de baja (elimina) un socio determinado en la BD
     * @param idSocio --> el ID del socio que se quiere dar de baja
     * @return diferentes cadenas de texto para validar excepciones o éxito de la operación
     * @Author Hugo
     */
    public String darDeBajaASocio(String idSocio) throws PersistenceException {

        EntityManager em = emf.createEntityManager();

        //Excepción ningún socio seleccionado
        if (idSocio == null) {
            em.close();
            return "Socio no seleccionado";
        }

        Socio s = em.find(Socio.class, idSocio);

        //Excepción socio tiene reservas activas
        Query q = em.createQuery("select r from Reserva r where r.idSocio.idSocio = :idRecibido");
        q.setParameter("idRecibido", idSocio);

        List<Reserva> reservasSocio = q.getResultList();

        if (!reservasSocio.isEmpty()) {
            em.close();
            return "Socio con reservas activas";
        }

        //Si ha pasado por todas las verificaciones, entonces ya podemos eliminar al socio de la base de la base de datos

        em.getTransaction().begin();
        em.remove(s);
        em.getTransaction().commit();

        em.close();
        return "Baja validada";

    }

}
