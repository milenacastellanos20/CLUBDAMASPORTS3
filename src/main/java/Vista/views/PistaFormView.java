package Vista.views;

import Entidades.Pista;
import Servicio.ClubService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class PistaFormView extends GridPane {
    public PistaFormView(ClubService club) {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        TextField id = new TextField();
        ComboBox<Pista.Deporte> deporte = new ComboBox<>();
        TextField descripcion = new TextField();
        CheckBox disponible = new CheckBox("Disponible");
        Button crear = new Button("Crear");

        addRow(0, new Label("idPista*"), id);
        addRow(1, new Label("Deporte*"), deporte);
        addRow(2, new Label("Descripción"), descripcion);
        addRow(3, new Label("Operativa"), disponible);
        add(crear, 1, 4);

        deporte.getItems().addAll(Pista.Deporte.values());

        crear.setOnAction(e -> {
            try {
                Pista p = new Pista(
                        id.getText(),
                        deporte.getValue(),
                        descripcion.getText(),
                        disponible.isSelected()
                );

                String resultado = club.insertarPista(p);

                switch (resultado) {
                    case "Inserción validada":
                        showInfo("Pista insertada correctamente");
                        break;

                    case "ID vacío":
                        showError("Pista no insertada correctamente. Asegúrese de que el campo 'idPista' no esté vacío");
                        break;

                    case "Deporte vacío":
                        showError("Pista no insertada correctamente. Asegúrese de que el campo 'Deporte' no esté vacío");
                        break;

                    case "Pista existente":
                        showError("Pista no insertada correctamente. El ID de pista introducido ya existe en la base de datos");
                        break;

                    default:
                        showError("Error desconocido a la hora de insertar la pista en la base de datos");
                        break;
                }

            } catch (Exception ex) {
                showError("Error desconocido a la hora de insertar la pista en la base de datos");
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