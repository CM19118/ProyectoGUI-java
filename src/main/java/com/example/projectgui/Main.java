package com.example.projectgui;

import com.example.projectgui.Controller.*;
import com.example.projectgui.DatabaseConnection;
import com.example.projectgui.Models.Producto;
import com.example.projectgui.Models.Proveedor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Main implements Initializable {
    @FXML
    private Button btnCarrito, btnFacturas, btnInventario, btnProductos, btnVentas, btnProveedores;
    @FXML
    private StackPane centerPane;
    @FXML
    private SplitMenuButton splitMenuBtn;
    @FXML
    //private MenuItem asistenciaOptions;

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
            String rol = userSession.getRol();
            int idUsuario = userSession.getIdUsuario();
            int idEmpleado = userSession.getIdEmpleado();
            splitMenuBtn.setText(Integer.parseInt(rol) != 1 ? "ADMIN" : "EMPLEADO");
            System.out.println("EL NOMBRE DEL USUARIO ES: "+username);
        } else {
            // No hay sesión activa
        }
    }
    @FXML
    private void handleClick(ActionEvent event) {
        if (event.getSource() == btnProductos) {
            loadView("Producto.fxml");
        } else if (event.getSource() == btnInventario) {
            loadView("Inventario.fxml");
        } else if (event.getSource() == btnCarrito) {
            loadView("Carrito.fxml");
        } else if (event.getSource() == btnFacturas) {
            loadView("Factura.fxml");
        } else if(event.getSource() == btnVentas){
            loadView("Ventas.fxml");
        } else if(event.getSource() == btnProveedores){
            loadView("Proveedor.fxml");
        }
    }


    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projectgui/" + fxmlFile));
            Parent view = loader.load();

            // Obtengo el controlador secundario desde el FXMLLoader para obtener los datos del diseno fmxl.
            Object controller = loader.getController();

            // Esto reemplazaa el contenido del centro del StackPane
            centerPane.getChildren().clear();
            centerPane.getChildren().add(view);

            if (controller instanceof TblProductController) {
                TblProductController productoController = (TblProductController) controller;
                productoController.mostrarListaProducto();
            }
            else if (controller instanceof InventarioController) {
                InventarioController inventarioController = (InventarioController) controller;
                //inventarioController.mostrarListaInventario();
            } else if (controller instanceof CarritoController) {
                CarritoController carritoController = (CarritoController) controller;
                //carritoController.mostrarListaCarrito();
            } else if (controller instanceof FacturaController) {
                FacturaController facturaController = (FacturaController) controller;
                //facturaController.mostrarListaFactura();
            } else if(controller instanceof VentasController){
                VentasController ventasController = (VentasController) controller;
            } else if(controller instanceof  ProveedorController){
                ProveedorController proveedorController = (ProveedorController) controller;
            }


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

}
