package Entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pistas", schema = "club_dama")
public class Pista {
    @Id
    @Column(name = "id_pista", nullable = false, length = 36)
    private String idPista;

    @Lob
    @Column(name = "deporte", nullable = false)
    private String deporte;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @ColumnDefault("1")
    @Column(name = "disponible", nullable = false)
    private Boolean disponible = false;

    @OneToMany(mappedBy = "idPista")
    private Set<Entidades.Reserva> reservas = new LinkedHashSet<>();

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
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

    public Set<Entidades.Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Entidades.Reserva> reservas) {
        this.reservas = reservas;
    }

}