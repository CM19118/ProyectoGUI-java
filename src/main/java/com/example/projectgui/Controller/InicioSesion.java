package com.example.projectgui.Controller;

import com.example.projectgui.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        Scene scene = new Scene(fxmlLoader.load(), 1110, 640); //Creamos una nueva escena, con 1110 de ancho y 640 alto
        InicioSesion controller = fxmlLoader.getController(); // Obténgo el controlador
        controller.setPrimaryStage(stage); // Establecemos el Stage en el controlador
        stage.setTitle("INICIO DE SESION"); //Seteamos el tituloo de  la ventana GUI
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onBtnLoginClicked() throws IOException {
        String username =  txtUser.getText().toString(); //Obtengo los datos del textLabel el correo en este caso
        String password = txtPass.getText().toString(); //Obtengo los datos del textLabel el password.

        if(!password.isEmpty() && !username.isEmpty()){
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM tbl_usuarios WHERE correo = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Autenticación exitosa
                    // Abrimos la nueva ventana, la ventana principal del sistema!
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


