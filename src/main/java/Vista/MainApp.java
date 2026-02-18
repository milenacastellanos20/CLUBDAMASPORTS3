package Vista;

import Entidades.*;
import Servicio.ClubService;
import vista.views.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.sql.SQLException;

public class MainApp extends Application {

    private ClubService club;
    private BorderPane root;
    private Label status;

    @Override
    public void start(Stage stage)  {
        try {
            club = new ClubDeportivo();

            club.cargarSocios();
            club.cargarPistas();
            club.cargarReservas();
        } catch (SQLException e) {
            showError("Error de conexion" + e.getMessage());
        }
        root = new BorderPane();
        root.setTop(buildMenuBar());
        status = new Label("Listo");
        status.setPadding(new Insets(4));
        root.setBottom(status);

        // Vista por defecto
        try {
            root.setCenter(new DashboardView(club));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root, 960, 640);
        stage.setTitle("Club DAMA Sports");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar buildMenuBar() {
        MenuBar mb = new MenuBar();

        Menu socios = new Menu("Socios");
        MenuItem altaSocio = new MenuItem("Alta socio");
        altaSocio.setOnAction(e -> root.setCenter(new SocioFormView(club)));
        MenuItem bajaSocio = new MenuItem("Baja socio");
        bajaSocio.setOnAction(e -> {
            try {
                root.setCenter(new BajaSocioView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        socios.getItems().addAll(altaSocio, bajaSocio);

        Menu pistas = new Menu("Pistas");
        MenuItem altaPista = new MenuItem("Alta pista");
        altaPista.setOnAction(e -> root.setCenter(new PistaFormView(club)));
        MenuItem cambiarDisp = new MenuItem("Cambiar disponibilidad");
        cambiarDisp.setOnAction(e -> {
            try {
                root.setCenter(new CambiarDisponibilidadView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        pistas.getItems().addAll(altaPista, cambiarDisp);

        Menu reservas = new Menu("Reservas");
        MenuItem crearReserva = new MenuItem("Crear reserva");
        crearReserva.setOnAction(e -> {
            try {
                root.setCenter(new ReservaFormView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        MenuItem cancelarReserva = new MenuItem("Cancelar reserva");
        cancelarReserva.setOnAction(e -> {
            try {
                root.setCenter(new CancelarReservaView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        reservas.getItems().addAll(crearReserva, cancelarReserva);

        Menu ver = new Menu("Ver");
        MenuItem dashboard = new MenuItem("Dashboard");
        dashboard.setOnAction(e -> {
            try {
                root.setCenter(new DashboardView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        ver.getItems().addAll(dashboard);

        Menu archivo = new Menu("Archivo");

        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(e -> {
            try {
                //Lammo al método del modelo para guardar antes de salir
            } catch (Exception ignored) {}
            Platform.exit();
        });
        archivo.getItems().addAll(salir);

        mb.getMenus().addAll(archivo, socios, pistas, reservas, ver);
        return mb;
    }



    public void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }

    @Override
    public void stop() throws Exception {
        try {
            //   LLamo al método del modelo para guardar los datos
        } catch (Exception ignored) {}
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
