package com.example.projectgui;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.projectgui.SessionManager;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioSesion extends Application {

    @FXML
    private TextField txtPass, txtUser;
    @FXML
    private StackPane panelCentral;
    @FXML
    private AnchorPane panelLogin, panelRegister;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectgui/InicioSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1110, 640);
        InicioSesion controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        stage.setTitle("INICIO DE SESION");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBtnLoginClicked() throws IOException {
        String username =  txtUser.getText().toString(); //Obtengo los datos del textLabel el correo en este caso
        String password = txtPass.getText().toString(); //Obtengo los datos del textLabel el password.

        if(!password.isEmpty() && !username.isEmpty()){
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM tbl_usuarios WHERE usuario = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Autenticación exitosa
                    // Almacenamos los datos del usuario que ha iniciado sesion (Manejo de sesiones)
                    String usernamee = resultSet.getString("usuario"); // Obtengo el nombre de usuario de la base de datos
                    String rol = resultSet.getString("rol"); // Obtengo el rol de la base de datos
                    int idUsuario = resultSet.getInt("idUsuario"); // Obtengo el ID de usuario de la base de datos
                    int idEmpleado = resultSet.getInt("idEmpleado"); // Obtengo el ID de empleado de la base de datos

                    // Almacenamos los datos del usuario que ha iniciado sesión (Manejo de sesiones)
                    UserSession userSession = new UserSession(usernamee, rol, idUsuario, idEmpleado);
                    SessionManager.setUsuarioSesion(userSession); //Le pasamos el objeto userSession con los datos obtenidos del usuario
                    //Al manejador de sesiones para poder usarlas desde cualquier clase.


                    // Abrimos la nueva ventana, la ventana principal del sistema(el main)!
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectgui/Main.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1420, 960);
                    primaryStage.setTitle("MENU PRINCIPAL");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("CREDENCIALES ERRONEAS!! :(");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("HAY CAMPOS VACIOS!! :(");
            alert.showAndWait();
        }
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

}


