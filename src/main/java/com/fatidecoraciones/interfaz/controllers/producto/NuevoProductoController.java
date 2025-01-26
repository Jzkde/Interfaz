package com.fatidecoraciones.interfaz.controllers.producto;

import com.fatidecoraciones.interfaz.models.Marca;
import com.fatidecoraciones.interfaz.models.Producto;
import com.fatidecoraciones.interfaz.services.MarcaService;
import com.fatidecoraciones.interfaz.services.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NuevoProductoController {


    private final MarcaService marcaService = new MarcaService();
    private final ProductoService productoService = new ProductoService();
    @FXML
    private TextField artField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField precioField;
    @FXML
    private ComboBox<Marca> marcaComboBox;
    @FXML
    private CheckBox esTelaCheckBox;
    private Runnable productoListener;
    public void setProductoListener(Runnable listener) {
        this.productoListener = listener;
    }
    public void initialize() {
        loadMarcas();
    }
    public void loadMarcas() {
        try {
            ObservableList<Marca> marcas = FXCollections.observableArrayList(marcaService.getMarcas());

            marcaComboBox.setItems(marcas);

            //Configurar cómo se muestran los datos en el ComboBox
            marcaComboBox.setCellFactory(cell -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(Marca item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getMarca());
                }
            });

            // Configurar el valor que se muestra al seleccionar un ítem
            marcaComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(Marca item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getMarca());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            // Aquí podrías mostrar un mensaje de error en la UI
        }
    }
    @FXML
    private void guardar() {
        try {
            String nombre = nombreField.getText();
            String art = artField.getText();
            double precio = Double.parseDouble(precioField.getText());
            boolean esTela = esTelaCheckBox.isSelected();
            Marca marca = marcaComboBox.getValue();

            if (nombre.isEmpty() || art.isEmpty() || marca == null) {
                mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
                return;
            }

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setArt(art);
            producto.setPrecio(precio);
            producto.setEsTela(esTela);

            // Guardar el producto en la base de datos
            productoService.nuevo(producto, marca.getMarca());

            // Notificar al controlador principal que se ha creado un producto
            if (productoListener != null) {
                productoListener.run(); // Esto actualizará la lista en la vista principal
            }

            cerrarVentana();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al guardar el producto.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
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

