package com.example.projectgui.Controller;

import com.example.projectgui.DatabaseConnection;
import com.example.projectgui.Models.Venta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentasController{

    //--------------- Variables para hacer la conexion a la base de datos --------------------------
    private Connection conexion;
    private Statement estado; //Para el estado de la conexion
    private ResultSet resultado; //Para el resultado de la base de datos

    @FXML
    public TableView<Venta> tablaVentas;
    @FXML
    public TableColumn<Venta, Integer> columnIdVenta;
    @FXML
    public TableColumn<Venta, String> columnProductos;
    @FXML
    public TableColumn<Venta, String> columnFechaVenta;
    @FXML
    public TableColumn<Venta, Integer> columnCantidadProducto;
    @FXML
    public TableColumn<Venta, Double> columnTotalProdcuto;


    public void initialize()
    {
        columnIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
        columnProductos.setCellValueFactory(new PropertyValueFactory<>("productos"));
        columnFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        columnCantidadProducto.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        columnTotalProdcuto.setCellValueFactory(new PropertyValueFactory<>("total"));

        cargarRegistroDeVentas();
    }

    private void cargarRegistroDeVentas() {

        List<Venta> listaDeVentas = new ArrayList<>();

        conexion = DatabaseConnection.getConnection();
        String consultaVentas = "SELECT idVenta, productos, fechaVenta, cantidadProducto, total FROM tbl_ventas;";

        try {
            listaDeVentas.clear();
            estado = conexion.createStatement();
            resultado = estado.executeQuery(consultaVentas);

            while (resultado.next())
            {
                int idVenta = resultado.getInt("idVenta");
                String productos = resultado.getString("productos");
                String fechaVenta = resultado.getString("fechaVenta");
                int cantidadProductos = resultado.getInt("cantidadProducto");
                double total = resultado.getDouble("total");


                Venta venta = new Venta(idVenta,fechaVenta,productos,cantidadProductos,total);
                listaDeVentas.add(venta);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        ObservableList<Venta> listaDeVentasObservable = FXCollections.observableArrayList(listaDeVentas);
        tablaVentas.setItems(listaDeVentasObservable);

    }


}
