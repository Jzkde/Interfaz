package com.fatidecoraciones.interfaz.controllers;

import com.fatidecoraciones.interfaz.models.Marca;
import com.fatidecoraciones.interfaz.models.Producto;
import com.fatidecoraciones.interfaz.services.MarcaService;
import com.fatidecoraciones.interfaz.services.ProductoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MarcaController {
    @FXML
    private TableView<Producto> productoTableView;
    @FXML
    private TableColumn<Producto, Long> idColumn;
    @FXML
    private TableColumn<Producto, String> artColumn;
    @FXML
    private TableColumn<Producto, String> nombreColumn;
    @FXML
    private TableColumn<Producto, Double> precioColumn;
    @FXML
    private TableColumn<Producto, Boolean> esTelaColumn;
    @FXML
    private TableColumn<Producto, String> marcaColumn;


    @FXML
    private ComboBox<Marca> marcaComboBox;


    private final MarcaService marcaService = new MarcaService();
    private final ProductoService productoService = new ProductoService();

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        artColumn.setCellValueFactory(new PropertyValueFactory<>("art"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        esTelaColumn.setCellValueFactory(new PropertyValueFactory<>("esTela"));
        esTelaColumn.setCellFactory(column -> new TableCell<Producto, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Sí" : "No"); // Convertir booleano a "Sí" o "No"
                }
            }
        });

        marcaColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMarca() != null ? cellData.getValue().getMarca().getMarca() : "Sin Marca")
        );

        loadMarcas();
        loadProductos();
    }

    private void loadMarcas() {
        try {
            ObservableList<Marca> posts = FXCollections.observableArrayList(marcaService.getMarcas());

            marcaComboBox.setItems(posts);

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

    private void loadProductos() {
        try {
            ObservableList<Producto> productos = FXCollections.observableArrayList(productoService.getProductos());
            productoTableView.setItems(productos);
        } catch (Exception e) {
            e.printStackTrace();
            // Aquí podrías mostrar un mensaje de error en la UI
        }
    }
}