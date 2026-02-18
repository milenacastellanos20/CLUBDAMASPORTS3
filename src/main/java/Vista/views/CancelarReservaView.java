package Vista.views;
import Servicio.ClubService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.function.Consumer;

public class CancelarReservaView extends GridPane {
    public CancelarReservaView(ClubService club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        ComboBox<String> id = new ComboBox();
        Button cancelar = new Button("Cancelar reserva");

        addRow(0, new Label("Reserva"), id);
        add(cancelar, 1, 1);

        //llamada al metodo del modelo para cargar las reservas en el combobox
        id.getItems().addAll(club.cargarReservasComboBox());

        cancelar.setOnAction(e -> {
            try {

                if (club.cancelarReserva(id.getValue())) {
                    showInfo("Reserva cancelada con éxito");
                } else {
                    showError("No ha seleccionado ninguna reserva");
                }

            } catch (Exception ex) {
                showError("Error desconocido al intentar eliminar la reserva de la base de datos");
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
