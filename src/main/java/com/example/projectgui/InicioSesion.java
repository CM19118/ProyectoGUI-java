package com.example.projectgui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioSesion extends Application {

    @FXML
    private TextField txtPass, txtUser;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectgui/InicioSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1110, 640);
        InicioSesion controller = fxmlLoader.getController();
        stage.initStyle(StageStyle.UTILITY);
        controller.setPrimaryStage(stage);

        stage.setTitle("INICIO DE SESION");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void onBtnLoginClicked() throws IOException {
        String username = txtUser.getText().toString();
        String password = txtPass.getText().toString();

        if (!password.isEmpty() && !username.isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT idUsuario, idEmpleado, password FROM tbl_usuarios WHERE usuario = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String storedHashFromDatabase = resultSet.getString("password");
                    int idUsuario = resultSet.getInt("idUsuario");
                    int idEmpleado = resultSet.getInt("idEmpleado");

                    if (BCrypt.checkpw(password, storedHashFromDatabase)) {
                        // Autenticación exitosa
                        // Almacenamos los datos del usuario que ha iniciado sesion (Manejo de sesiones)
                        String rol = obtenerRolDeLaBaseDeDatos(connection, idUsuario); // Obtén el rol del usuario
                        UserSession userSession = new UserSession(username, rol, idUsuario, idEmpleado);
                        SessionManager.setUsuarioSesion(userSession);

                        // Abrimos la nueva ventana, la ventana principal del sistema(el main)!
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectgui/Main.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());

                        //primaryStage.initStyle(StageStyle.UTILITY);
                        primaryStage.setTitle("MENU PRINCIPAL");
                        primaryStage.setScene(scene);
                        primaryStage.setFullScreen(true);
                        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                        primaryStage.show();
                    } else {
                        mostrarError("CREDENCIALES ERRÓNEAS");
                    }
                } else {
                    mostrarError("CREDENCIALES ERRÓNEAS");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            mostrarError("HAY CAMPOS VACÍOS");
        }
    }

    // Función para mostrar un mensaje de error en un cuadro de diálogo
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Función para obtener el rol del usuario desde la base de datos
    private String obtenerRolDeLaBaseDeDatos(Connection connection, int idUsuario) throws SQLException {
        String rol = "RolPorDefecto"; // Establece un valor por defecto si no puedes obtener el rol desde la base de datos
        String query = "SELECT rol FROM tbl_usuarios WHERE idUsuario = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idUsuario);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            rol = resultSet.getString("rol");
        }
        return rol;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

}


