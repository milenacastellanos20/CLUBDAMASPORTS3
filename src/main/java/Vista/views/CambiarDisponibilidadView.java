package Vista.views;

import Servicio.ClubService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.util.function.Consumer;

public class CambiarDisponibilidadView extends GridPane {
    public CambiarDisponibilidadView(ClubService club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<String> id = new ComboBox();
        CheckBox disponible = new CheckBox("Disponible");
        Button cambiar = new Button("Aplicar");

        addRow(0, new Label("idPista"), id);
        addRow(1, new Label("Estado"), disponible);
        add(cambiar, 1, 2);

        id.getItems().addAll(club.cargarPistasCombobox());

        cambiar.setOnAction(e -> {
            try {

                if (club.cambiarDisponibilidadPista(id.getValue(), disponible.isSelected())) {
                    showInfo("Disponibilidad actualizada correctamente");
                } else {
                    showError("No ha seleccionado ninguna pista");
                }

            } catch (Exception ex) {
                showError(ex.getMessage());
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