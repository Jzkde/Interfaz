package com.fatidecoraciones.interfaz.controllers.marcas;

import com.fatidecoraciones.interfaz.models.Marca;
import com.fatidecoraciones.interfaz.services.MarcaService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditarMarcaController {
    private final MarcaService marcaService = new MarcaService();
    @FXML
    private TextField marcaField;
    private Marca marca;

    private OnMarcaEditListener editListener;

    public void setEditListener(EditarMarcaController.OnMarcaEditListener editListener) {
        this.editListener = editListener;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;

        // Rellenar los campos con la información del producto
        marcaField.setText(marca.getMarca());
    }

    @FXML
    private void guardar() throws Exception {

        // Actualizar el producto con los nuevos valores
        marca.setMarca(marcaField.getText());

        // Persistir los cambios
        marcaService.editar(marca, marca.getId());

        if (editListener != null) {
            editListener.onProductoEdit();
        }
        // Cerrar la ventana de edición
        marcaField.getScene().getWindow().hide();
    }

    public interface OnMarcaEditListener {
        void onProductoEdit(); // Método que notifica la edición
    }

}
