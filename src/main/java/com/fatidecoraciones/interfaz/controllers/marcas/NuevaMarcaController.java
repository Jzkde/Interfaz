package com.fatidecoraciones.interfaz.controllers.marcas;

import com.fatidecoraciones.interfaz.models.Marca;
import com.fatidecoraciones.interfaz.models.Producto;
import com.fatidecoraciones.interfaz.services.MarcaService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NuevaMarcaController {

    private final MarcaService marcaService = new MarcaService();
    @FXML
    private TextField marcaField;

    private Runnable marcaListener;

    public void setMarcaListener(Runnable listener) {
        this.marcaListener = listener;
    }

    @FXML
    private void guardar() {
        try {
            String marcaN = marcaField.getText();

            if (marcaN.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
                return;
            }

            Marca marca = new Marca();
            marca.setMarca(marcaN);

            // Guardar el producto en la base de datos
            marcaService.nuevo(marca);

            // Notificar al controlador principal que se ha creado un producto
            if (marcaListener != null) {
                marcaListener.run(); // Esto actualizará la lista en la vista principal
            }

            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al guardar el producto.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) marcaField.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}