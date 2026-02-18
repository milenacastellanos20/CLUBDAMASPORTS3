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


    /**
     * Inserta una nueva pista en la base de datos.
     * Primero comprueba si ya existe una pista con el mismo id.
     * Si no existe inicia transaccion y persiste
     *
     * @param pista Objeto Pista a registrar.
     * @return diferentes cadenas de texto para validar excepciones o éxito de la operación
     * @author Daniel
     */
    public String insertarPista(Pista pista) throws PersistenceException {
        EntityManager em = emf.createEntityManager();

        //campo ID vacío
        if (pista.getIdPista().trim().isEmpty()) {
            em.close();
            return "ID vacío";
        }

        //campo deporte vacío
        if (pista.getDeporte() == null) {
            em.close();
            return "Deporte vacío";
        }

        //si la pista ya existe no la inserta
        if (em.find(Pista.class, pista.getIdPista()) != null) {
            em.close();
            return "Pista existente";
        }

        //si no existe la inserta
        em.getTransaction().begin();
        em.persist(pista);
        em.getTransaction().commit();
        em.close();
        return "Inserción validada";

    }

    /**
     * Obtiene una lista pista para cargar en un combobox.
     * @return devuelve una lista de Strings con los ids de las pistas.
     * @author Daniel
     */
    public List<String> cargarPistasCombobox() {
        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select p.id from Pista p");
        List<String> pistas = q.getResultList();

        return pistas;
    }


    /**
     * Modifica el estado de la disponibilidad de la pista
     *
     * @param idPista      El id de la pista a modificar.
     * @param seleccionado El nuevo estado de disponibilidad.
     * @return Devuelve true si se modifico correctamente, false si no.
     * @Author Daniel
     */
    public boolean cambiarDisponibilidadPista(String idPista, Boolean seleccionado) throws PersistenceException {
        EntityManager em = emf.createEntityManager();

        if (idPista == null) {
            return false; //si el id es nulo se cancela
        }

        try {

            Pista pista = em.find(Pista.class, idPista); //buscamos la pista por su id

            if (pista != null) {
                em.getTransaction().begin();
                pista.setDisponible(seleccionado);
                em.getTransaction().commit(); //comiteamos la transaccion
                em.close();
                return true;
            } else {

                em.close();
                return false; //si no se encuentra la pista para

            }


        } catch (Exception e) {
            return false;
        }
    }

}
