package Vista.views;
package Vista.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Entidades.Reserva;
import Servicio.ClubService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class ReservaFormView extends GridPane {
    public ReservaFormView(ClubService club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        TextField id = new TextField();
        ComboBox<String> idSocio = new ComboBox();
        ComboBox<String> idPista = new ComboBox();
        DatePicker fecha = new DatePicker(LocalDate.now());
        TextField hora = new TextField("10:00");
        Spinner<Integer> duracion = new Spinner<>(30, 300, 60, 30);
        TextField precio = new TextField("10.0");
        Button crear = new Button("Reservar");

        addRow(0, new Label("idReserva*"), id);
        addRow(1, new Label("Socio*"), idSocio);
        addRow(2, new Label("Pista*"), idPista);
        addRow(3, new Label("Fecha*"), fecha);
        addRow(4, new Label("Hora inicio* (HH:mm)"), hora);
        addRow(5, new Label("Duración (min)"), duracion);
        addRow(6, new Label("Precio (€)"), precio);
        add(crear, 1, 7);

        idSocio.getItems().addAll(club.cargarSociosComboBox());
        idPista.getItems().addAll(club.cargarPistasCombobox());

        crear.setOnAction(e -> {
            try {

                ArrayList<String> listaParametros = new ArrayList<>();
                listaParametros.add(id.getText());
                listaParametros.add(idSocio.getValue());
                listaParametros.add(idPista.getValue());
                listaParametros.add(fecha.getValue() == null ? null : fecha.getValue().toString());
                listaParametros.add(hora.getText());
                listaParametros.add(duracion.getValue().toString());
                listaParametros.add(precio.getText());

                String resultado = club.insertarReserva(listaParametros);

                switch (resultado) {
                    case "Reserva validada":
                        showInfo("Reserva insertada correctamente");
                        break;

                    case "ID existente":
                        showError("Reserva no insertada correctamente. Asegúrese de que el ID no está repetido");
                        break;

                    case "Fecha anterior":
                        showError("Reserva no insertada correctamente. La fecha de reserva introducida es anterior a la actual");
                        break;

                    case "Pista no disponible":
                        showError("Reserva no insertada correctamente. La pista seleccionada no está disponible en este momento");
                        break;

                    case "Fecha de pista no válida":
                        showError("Reserva no insertada correctamente. Esta pista ya está asignada a otra reserva en este momento");
                        break;

                    case "ID vacío":
                        showError("Reserva no insertada correctamente. Asegúrese de que el campo 'idReserva' no esté vacío");
                        break;

                    case "Socio vacío":
                        showError("Reserva no insertada correctamente. Asegúrese de que el campo 'Socio' no esté vacío");
                        break;

                    case "Pista vacía":
                        showError("Reserva no insertada correctamente. Asegúrese de que el campo 'Pista' no esté vacío");
                        break;

                    case "Fecha vacía":
                        showError("Reserva no insertada correctamente. Asegúrese de que el campo 'Fecha' no esté vacío");
                        break;

                    case "Hora vacía":
                        showError("Reserva no insertada correctamente. Asegúrese de que el campo 'Hora inicio' no esté vacío");
                        break;

                    default:
                        showError("Error desconocido al insertar la reserva en la base de datos");
                        break;
                }

            } catch (Exception ex) {
                showError("Error desconocido al insertar la reserva en la base de datos");
            }
        });

    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
