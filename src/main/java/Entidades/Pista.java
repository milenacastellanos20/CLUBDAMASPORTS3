package Entidades;

import jakarta.persistence.*;
import Servicio.DeporteConverter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "pistas")
public class Pista {
    @Id
    @Column(name = "id_pista", nullable = false, length = 36)
    private String idPista;


    public enum Deporte {

        FUTBOL_SALA("fútbol sala"),
        TENIS("tenis"),
        PADEL("pádel");

        private final String deporte;

        Deporte(String deporte) {
            this.deporte = deporte;
        }

        public String getDeporte() {
            return deporte;
        }

        @Override
        public String toString() {
            return deporte; //
        }

    }

    @Convert(converter = DeporteConverter.class)
    @Column(name = "deporte", nullable = false)
    private Deporte deporte;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @ColumnDefault("1")
    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    public String getIdPista() {
        return idPista;
    }

    public Pista(String idPista, Deporte deporte, String descripcion, Boolean disponible) {
        this.idPista = idPista;
        this.deporte = deporte;
        this.descripcion = descripcion;
        this.disponible = disponible;
    }

    public Pista() {

    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

}
