package com.example.projectgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Main implements Initializable {
    @FXML
    private Text textoMain;
    @FXML
    private Button btnCarrito, btnFacturas, btnInventario, btnProductos, btnVentas, btnProveedores, btnReparaciones;
    @FXML
    private MenuItem opcCrearEmpleado, opcCrearUsuarios;
    @FXML
    private StackPane centerPane;
    @FXML
    private SplitMenuButton splitMenuBtn;
    //INSTANCIAS PARA LA BASE DE DATOS
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    ////////////////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Obtenemos la instancia de UserSession desde SesionManager
        UserSession userSession = SessionManager.getUsuarioSesion();

        // Verificamos si la sesión existe y obtenemos datos
        if (userSession != null) {
            String username = userSession.getUsername();
            int rol = Integer.parseInt(userSession.getRol());
            manejodeVistas(rol);
            int idUsuario = userSession.getIdUsuario();
            int idEmpleado = userSession.getIdEmpleado();

        } else {
            // No hay sesión activa
        }
    }
    @FXML
    private void handleClick(ActionEvent event) {
        if (event.getSource() == btnProductos) {
            loadView("Producto.fxml");
            textoMain.setText("Producto");
        } else if (event.getSource() == btnInventario) {
            loadView("Inventario.fxml");
            textoMain.setText("Inventario");
        } else if (event.getSource() == btnCarrito) {
            loadView("Carrito.fxml");
            textoMain.setText("Carrito");
        } else if (event.getSource() == btnFacturas) {
            loadView("Factura.fxml");
            textoMain.setText("Factura");
        } else if(event.getSource() == btnVentas){
            loadView("Ventas.fxml");
            textoMain.setText("Ventas");
        } else if(event.getSource() == btnProveedores){
            loadView("Proveedor.fxml");
            textoMain.setText("Proveedor");
        }else if(event.getSource() == btnReparaciones){
            loadView("Reparaciones.fxml");
            textoMain.setText("Reparaciones");
        }
    }

    private void manejodeVistas(int rol){
        if(rol == 0){
            splitMenuBtn.setText("Admin");
        }else if(rol == 1){
            splitMenuBtn.setText("Empleado");
            opcCrearEmpleado.setVisible(false);
            opcCrearUsuarios.setVisible(false);
            btnProveedores.setVisible(false);
        }else{
            splitMenuBtn.setText("Tecnico");
            opcCrearEmpleado.setVisible(false);
            opcCrearUsuarios.setVisible(false);
            btnProveedores.setVisible(false);
            btnCarrito.setVisible(false);
            btnVentas.setVisible(false);
            btnProductos.setVisible(false);
        }
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectgui/" + fxmlFile));
            Parent view = loader.load();

            // Obtengo el controlador secundario desde el FXMLLoader para obtener los datos del diseno fmxl.
            Object controller = loader.getController();

            // Esto reemplazaa el contenido del centro del StackPane (panel principal del main donde se muestra la informacion)
            centerPane.getChildren().clear();
            centerPane.getChildren().add(view);

            /*
            if (controller instanceof TblProductController) {
                TblProductController productoController = (TblProductController) controller;
                productoController.mostrarListaProducto();
            }
        */

        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores al cargar la vista
        }
    }

    @FXML
    private void openFormAsistencia(){
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectgui/formAsistencia.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            stage.setScene(scene); // Usa el Stage local
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFormEmpleados(){
        loadView("Empleado.fxml");
    }
    @FXML
    private void openFormUsuarios(){
        loadView("Usuario.fxml");
    }

}
