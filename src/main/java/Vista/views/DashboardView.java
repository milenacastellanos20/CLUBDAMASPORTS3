package org.example.Vista.views;


import Entidades.Pista;
import Entidades.Reserva;
import Entidades.Socio;
import Servicio.ClubService;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import java.sql.SQLException;
import java.util.function.Consumer;

public class DashboardView extends BorderPane {
    public DashboardView(ClubService club) throws SQLException {
        setPadding(new Insets(10));
        Label title = new Label("Resumen");
        setTop(title);
        TableView<Socio> tablaSocios = new TableView<>();
        TableColumn<Socio, String> c1 = new TableColumn<>("ID");
        c1.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdSocio()));
        TableColumn<Socio, String> c2 = new TableColumn<>("Dni");
        c2.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getDni()));
        TableColumn<Socio, String> c3 = new TableColumn<>("Nombre");
        c3.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getNombre()));
        TableColumn<Socio, String> c4 = new TableColumn<>("Apellidos");
        c4.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getApellidos()));
        TableColumn<Socio, String> c5 = new TableColumn<>("Teléfono");
        c5.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getTelefono()));
        TableColumn<Socio, String> c6 = new TableColumn<>("Email");
        c6.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getEmail()));
        tablaSocios.getColumns().addAll(c1, c2, c3, c4, c5, c6);
        tablaSocios.getItems().addAll(club.cargarSociosDashBoard());

        TableView<Pista> tablaPistas = new TableView<>();
        TableColumn<Pista, String> p1 = new TableColumn<>("ID");
        p1.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdPista()));
        TableColumn<Pista, String> p2 = new TableColumn<>("Deporte");
        p2.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getDeporte().toString()));
        TableColumn<Pista, String> p3 = new TableColumn<>("Disponible");
        p3.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getDisponible())));
        tablaPistas.getColumns().addAll(p1, p2, p3);
        tablaPistas.getItems().addAll(club.cargarPistasDashBoard());

        TableView<Reserva> tablaReservas = new TableView<>();
        TableColumn<Reserva, String> r1 = new TableColumn<>("ID");
        r1.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdReserva()));
        TableColumn<Reserva, String> r2 = new TableColumn<>("Socio");
        r2.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdSocio().getIdSocio()));
        TableColumn<Reserva, String> r3 = new TableColumn<>("Pista");
        r3.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getIdPista().getIdPista()));
        TableColumn<Reserva, String> r4 = new TableColumn<>("Fecha");
        r4.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getFecha().toString()));
        TableColumn<Reserva, String> r5 = new TableColumn<>("Inicio");
        r5.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getHoraInicio().toString()));
        TableColumn<Reserva, String> r6 = new TableColumn<>("Min");
        r6.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(String.valueOf(p.getValue().getDuracionMin())));
        tablaReservas.getColumns().addAll(r1, r2, r3, r4, r5, r6);
        tablaReservas.getItems().addAll(club.cargarReservasDashBoard());

        BorderPane center = new BorderPane();
        center.setTop(new Label("Socios"));
        center.setCenter(tablaSocios);
        BorderPane right = new BorderPane();
        right.setTop(new Label("Pistas"));
        right.setCenter(tablaPistas);
        setCenter(center);
        setRight(right);
        setBottom(tablaReservas);
    }
}
