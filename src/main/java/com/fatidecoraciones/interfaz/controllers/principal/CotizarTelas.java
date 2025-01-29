package com.fatidecoraciones.interfaz.controllers.principal;

import com.fatidecoraciones.interfaz.models.Producto;
import com.fatidecoraciones.interfaz.services.ProductoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CotizarTelas {
    private final ProductoService productoService = new ProductoService();

    @FXML
    private ComboBox<Producto> comboBoxTelas;
    @FXML
    private ComboBox<Producto> comboBoxAccesorios;
    @FXML
    private TableView<Producto> tableViewProductos;
    @FXML
    private TableColumn<Producto, Long> colId;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Void> colAcciones;
    @FXML
    private TextField anchoField;
    @FXML
    private TextField propField;
    @FXML
    private TextField buscarTela;
    @FXML
    private TextField buscarAccesorio;
    private final ObservableList<Producto> productosSeleccionados = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
        // Configurar las columnas del TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Configurar el TableView para mostrar los productos seleccionados
        tableViewProductos.setItems(productosSeleccionados);

        // Configurar la columna de acciones con un botón
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminar = new Button("Quitar");

            {
                btnEliminar.setOnAction(event -> {
                    Producto producto = getTableView().getItems().get(getIndex());
                    productosSeleccionados.remove(producto);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                }
            }
        });
        cargarTelas();
        cargarAccesorios();
    }
    public void cargarTelas() throws Exception {
        // Obtener la lista completa de productos
        ObservableList<Producto> productos = FXCollections.observableArrayList(productoService.getProductos());

        // Filtrar los productos con esTela = true y buscar texto
        ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList(
                productos.stream()
                        .filter(producto -> producto.getEsTela())  // Filtra por esTela = true
                        .filter(producto -> producto.getNombre().toLowerCase().contains(buscarTela.getText().toLowerCase())) // Filtro por nombre usando el texto de búsqueda
                        .collect(Collectors.toList())
        );

        // Asignar la lista filtrada al ComboBox
        comboBoxTelas.setItems(productosFiltrados);

        // Configurar cómo se muestran los datos en el ComboBox
        comboBoxTelas.setCellFactory(cell -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });

        // Configurar el valor que se muestra al seleccionar un ítem
        comboBoxTelas.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });

        // Agregar un listener al TextField 'buscar' para filtrar cuando el texto cambie
        buscarTela.textProperty().addListener((observable, oldValue, newValue) -> {
            // Volver a filtrar la lista de productos cuando cambia el texto de búsqueda
            ObservableList<Producto> productosFiltradosPorBusqueda = FXCollections.observableArrayList(
                    productos.stream()
                            .filter(producto -> producto.getEsTela())  // Filtra por esTela = true
                            .filter(producto -> producto.getNombre().toLowerCase().contains(newValue.toLowerCase())) // Filtro por nombre usando el texto de búsqueda
                            .collect(Collectors.toList())
            );
            comboBoxTelas.setItems(productosFiltradosPorBusqueda);
        });
    }
    public void cargarAccesorios() throws Exception {
        // Obtener la lista completa de productos
        ObservableList<Producto> productos = FXCollections.observableArrayList(productoService.getProductos());

        // Filtrar los productos con esTela = false y buscar texto
        ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList(
                productos.stream()
                        .filter(producto -> !producto.getEsTela())
                        .filter(producto -> producto.getNombre().toLowerCase().contains(buscarAccesorio.getText().toLowerCase())) // Filtro por nombre usando el texto de búsqueda
                        .collect(Collectors.toList())
        );

        // Asignar la lista filtrada al ComboBox
        comboBoxAccesorios.setItems(productosFiltrados);

        // Configurar cómo se muestran los datos en el ComboBox
        comboBoxAccesorios.setCellFactory(cell -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });

        // Configurar el valor que se muestra al seleccionar un ítem
        comboBoxAccesorios.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });

        // Agregar un listener al TextField 'buscar' para filtrar cuando el texto cambie
        buscarAccesorio.textProperty().addListener((observable, oldValue, newValue) -> {
            // Volver a filtrar la lista de productos cuando cambia el texto de búsqueda
            ObservableList<Producto> productosFiltradosPorBusqueda = FXCollections.observableArrayList(
                    productos.stream()
                            .filter(producto -> !producto.getEsTela())
                            .filter(producto -> producto.getNombre().toLowerCase().contains(newValue.toLowerCase())) // Filtro por nombre usando el texto de búsqueda
                            .collect(Collectors.toList())
            );
            comboBoxAccesorios.setItems(productosFiltradosPorBusqueda);
        });
    }

    @FXML
    private void agregarProducto() {
        Producto productoSeleccionado = comboBoxAccesorios.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            productosSeleccionados.add(productoSeleccionado);
        }
    }
    @FXML
    private void cotizar() throws Exception {
        String tela = comboBoxTelas.getValue().getNombre();
        Float anncho = Float.parseFloat(anchoField.getText());
        Float prop = Float.parseFloat(propField.getText());
        List<Producto> productos = new ArrayList<>(productosSeleccionados);
        productoService.cotizar(productosSeleccionados, tela, anncho, prop, "PREsilla");
    }
}