package Vista.views;

import jakarta.persistence.PersistenceException;
import org.example.Entidades.Socio;
import org.example.Servicio.ClubService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class SocioFormView extends GridPane {
    public SocioFormView(ClubService club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        TextField id = new TextField();
        TextField dni = new TextField();
        TextField nombre = new TextField();
        TextField apellidos = new TextField();
        TextField tel = new TextField();
        TextField email = new TextField();
        Button crear = new Button("Crear");

        addRow(0, new Label("idSocio*"), id);
        addRow(1, new Label("DNI*"), dni);
        addRow(2, new Label("Email*"), email);
        addRow(3, new Label("Nombre"), nombre);
        addRow(4, new Label("Apellidos"), apellidos);
        addRow(5, new Label("Teléfono"), tel);
        add(crear, 1, 6);

        crear.setOnAction(e -> {
            try {
                Socio s = new Socio(
                        id.getText(),
                        dni.getText(),
                        nombre.getText(),
                        apellidos.getText(),
                        tel.getText(),
                        email.getText()
                );

                String resultado = club.insertarSocio(s);

                switch (resultado) {
                    case "inserción validada":
                        showInfo("Socio insertado en la base de datos con éxito");
                        break;

                    case "ID vacío":
                        showError("El campo 'idSocio' no puede estar vacío");
                        break;

                    case "DNI vacío":
                        showError("El campo 'DNI' no puede estar vacío");
                        break;

                    case "EMAIL vacío":
                        showError("El campo 'email' no puede estar vacío");
                        break;

                    case "DNI repetido":
                        showError("El DNI introducido ya existe en la base de datos");
                        break;

                    case "EMAIL repetido":
                        showError("El email introducido ya existe en la base de datos");
                        break;

                    case "Socio ya existente":
                        showError("El ID de socio ya existe en la base de datos");
                        break;

                    default:
                        showError("Error desconocido al insertar el socio");
                        break;
                }

            } catch (PersistenceException ex) {
                showError("Error al subir el socio a la base de datos");
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
