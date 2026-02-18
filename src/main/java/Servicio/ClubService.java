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
     * @param parametrosObjetoReserva --> una lista de parámetros necesarios para crear la reserva
     * @return diferentes cadenas de texto para validar excepciones o éxito de la operación
     * @author Milena
     * Metodo para insertar una reserva, si el idPista o el idSocio es null return false,
     * si no es null persistimos el objeto
     */
    public String insertarReserva(ArrayList<String> parametrosObjetoReserva) {

        //En este método, se ha tenido que recibir una lista de parámetros
        //para crear el objeto en la misma clase dado que para crear el objeto,
        //tenemos que crear dos objetos adicionales: socio y pista.
        //Esto haría que en la vista correspondiente hbiera mucha más lógica de la necesaria,
        //por lo que se ha optado por esta opción

        /*
        Nota para facilidad de desarrollo del método
        Estructura arrayList:

        0. id reserva
        1. id socio
        2. id pista
        3. fecha (convertir)
        4. hora (convertir)
        5. duracion (convertir)
        6. precio (convertir)

        */

        EntityManager em = emf.createEntityManager();

        //Comprobación campos vacíos

        //Campo ID reserva
        if (parametrosObjetoReserva.get(0) == null || parametrosObjetoReserva.get(0).trim().isEmpty()) {
            em.close();
            return "ID vacío";
        }

        //Campo ID socio
        if (parametrosObjetoReserva.get(1) == null) {
            em.close();
            return "Socio vacío";
        }

        //Campo ID pista
        if (parametrosObjetoReserva.get(2) == null) {
            em.close();
            return "Pista vacía";
        }
        //Campo Fecha
        if (parametrosObjetoReserva.get(3) == null) {
            em.close();
            return "Fecha vacía";
        }

        //Campo Hora
        if (parametrosObjetoReserva.get(4).isEmpty()) {
            em.close();
            return "Hora vacía";
        }

        // Cargar objeto socio y pista a partir de los datos recibidos
        Socio socio = em.find(Socio.class, parametrosObjetoReserva.get(1));
        Pista pista = em.find(Pista.class, parametrosObjetoReserva.get(2));

        // Parseo de datos
        LocalDate fecha = LocalDate.parse(parametrosObjetoReserva.get(3));
        LocalTime hora = LocalTime.parse(parametrosObjetoReserva.get(4));
        Integer duracion = Integer.parseInt(parametrosObjetoReserva.get(5));
        BigDecimal precio = new BigDecimal(parametrosObjetoReserva.get(6));

        Reserva r = new Reserva(parametrosObjetoReserva.get(0), socio, pista, fecha, hora, duracion, precio);

        //Comprobación reserva existente
        Reserva rExistente = em.find(Reserva.class, r.getIdReserva());

        if (rExistente != null) {
            em.close();
            return "ID existente";
        }

        //Comprobación fecha anterior a la actual
        if (r.getFecha().isBefore(LocalDate.now())) {
            em.close();
            return "Fecha anterior";
        }

        //Comprobación Pista no disponible
        if (!r.getIdPista().getDisponible()) {
            em.close();
            return "Pista no disponible";
        }

        //Comprobación de que la pista está disponible

        Pista pistaComprobacion = em.find(Pista.class, r.getIdPista().getIdPista());


        if (!pistaComprobacion.getDisponible()) {
            em.close();
            return "Pista no disponible";
        }


        //Comprobación fecha de pista no válida

        //Consulta para verificar todas las fechas asignadas a dicha pista
        Query fechasPista = em.createQuery("select r from Reserva r where r.idPista.idPista = :idPistaRecibido");
        fechasPista.setParameter("idPistaRecibido", r.getIdPista().getIdPista());
        List<Reserva> reservasConPistaSeleccionada = fechasPista.getResultList();

        boolean fechaValida = true;

        //Ahora, recorro la lista de reservas que contienen dicha pista asociada para verificar
        //cualquier fecha incorrecta

        for (Reserva reservaIteracion : reservasConPistaSeleccionada) {

            //Aquí decimos que si la fecha de reserva es exactamente el
            //mismo día que otra reserva ya establecida, no permita que se guarde
            //La fecha solamente va a poder ser posterior
            if (r.getFecha().equals(reservaIteracion.getFecha())) {
                fechaValida = false;
                break;
            }

        }

        if (!fechaValida) {
            em.close();
            return "Fecha de pista no válida";
        }

        //Si pasa todas las comprobaciones, entonces se guarda en la base de datos
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();
        em.close();
        return "Reserva validada";

    }


    /**
     * Obtiene una lista de las reservas para cargar en un combobox.
     * @return devuelve una lista de Strings con los ids de las reservas.
     * @author Milena
     */
    public List<String> cargarReservasComboBox() {
        EntityManager em = emf.createEntityManager();

        Query q = em.createQuery("select r.id from Reserva r");
        List<String> reservas = q.getResultList();

        em.close();
        return reservas;
    }

    /**
     * @author Milena
     * Metodo para cancelar una reserva recibiendo un id de reserva y comprobando que no sea nulo,
     * si es nulo return false. Si no es nulo se inicia la transaccion y lo elimina
     * @param idReserva
     * @return
     */
    public boolean cancelarReserva(String idReserva) {

        EntityManager em = emf.createEntityManager();

        if (idReserva == null) {
            em.close();
            return false;
        } else {

            Reserva reserva = em.find(Reserva.class, idReserva);
            em.getTransaction().begin();
            em.remove(reserva);
            em.getTransaction().commit();
            em.close();
            return true;

        }
    }
}
