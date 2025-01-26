package com.fatidecoraciones.interfaz.controllers.producto;

import com.fatidecoraciones.interfaz.models.Marca;
import com.fatidecoraciones.interfaz.models.Producto;
import com.fatidecoraciones.interfaz.services.MarcaService;
import com.fatidecoraciones.interfaz.services.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditarProductoController {

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
    private Producto producto;
    private OnProductoEditListener editListener; // Callback

    public void setEditListener(OnProductoEditListener editListener) {
        this.editListener = editListener;
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

    public void setProducto(Producto producto) {
        this.producto = producto;

        // Rellenar los campos con la información del producto
        nombreField.setText(producto.getNombre());
        artField.setText(producto.getArt());
        precioField.setText(String.valueOf(producto.getPrecio()));
        esTelaCheckBox.setSelected(producto.getEsTela());
        loadMarcas();
        for (Marca marca : marcaComboBox.getItems()) {
            if (marca.getMarca().equals(producto.getMarca().getMarca())) {
                marcaComboBox.setValue(marca); // Seleccionar la marca correspondiente
                break;
            }
        }
    }

    @FXML
    private void guardar() throws Exception {

        Marca marca = marcaComboBox.getValue();
        if (marca == null) {
            System.out.println("Debe seleccionar una marca antes de guardar.");
            return; // No continúes si no hay marca seleccionada
        }
        // Actualizar el producto con los nuevos valores
        producto.setNombre(nombreField.getText());
        producto.setArt(artField.getText());
        producto.setPrecio(Double.parseDouble(precioField.getText()));
        producto.setEsTela(esTelaCheckBox.isSelected());

        // Persistir los cambios
        productoService.editar(producto, marca.getMarca());

        if (editListener != null) {
            editListener.onProductoEdit();
        }
        // Cerrar la ventana de edición
        artField.getScene().getWindow().hide();
    }

    public interface OnProductoEditListener {
        void onProductoEdit(); // Método que notifica la edición
    }
}