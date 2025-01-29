package com.fatidecoraciones.interfaz.controllers.producto;

import com.fatidecoraciones.interfaz.models.Producto;
import com.fatidecoraciones.interfaz.services.ProductoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class ProductoController {
    private final ProductoService productoService = new ProductoService();
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
    private TableColumn<Producto, Void> accionesColumn;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();

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


        // Configurar la columna para que tenga dos botones: Modificar y Eliminar
        accionesColumn.setCellFactory(new Callback<TableColumn<Producto, Void>, TableCell<Producto, Void>>() {
            @Override
            public TableCell<Producto, Void> call(TableColumn<Producto, Void> param) {
                return new TableCell<Producto, Void>() {
                    private final Button btnModificar = new Button("Modificar");
                    private final Button btnEliminar = new Button("Eliminar");
                    private final HBox hbox = new HBox(10); // Espaciado de 10px entre los botones

                    {
                        hbox.getChildren().addAll(btnModificar, btnEliminar);

                        // Acción para el botón de Modificar
                        btnModificar.setOnAction(event -> {
                            Producto producto = getTableView().getItems().get(getIndex());
                            editarProducto(producto); // Llama a editarProducto con el producto seleccionado
                        });

                        // Acción para el botón de Eliminar
                        btnEliminar.setOnAction(event -> {
                            Producto producto = getTableView().getItems().get(getIndex());
                            eliminarProducto(producto);  // Llama a eliminarProducto con el producto seleccionado
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox); // Establecer el HBox con los botones en la celda
                        }
                    }
                };
            }
        });

        loadProductos();
    }

    private void eliminarProducto(Producto producto) {
        try {
            productoService.borrar(producto.getId()); // Llamar al servicio para eliminar el producto
            productoTableView.getItems().remove(producto); // Eliminar de la tabla
            System.out.println("Producto eliminado: " + producto.getNombre());
        } catch (Exception e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
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

    @FXML
    private void editarProducto(Producto producto) {
        try {
            // Cargar el archivo FXML para la ventana de edición
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/EditarProducto.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de edición
            EditarProductoController editarProductoController = loader.getController();
            editarProductoController.setProducto(producto); // Pasar el producto seleccionado a la ventana de edición

            // Crear una nueva escena para el diálogo de edición
            Scene dialogScene = new Scene(root);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Producto");
            dialogStage.setScene(dialogScene);
            dialogStage.showAndWait(); // Esperar a que se cierre el diálogo
            loadProductos(); // Volver a cargar los productos para reflejar los cambios
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void nuevoProducto() throws Exception {

        // Llamar a la ventana de edición con un producto vacío
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/NuevoProducto.fxml"));
        Parent root = loader.load();

        NuevoProductoController controller = loader.getController();
        controller.setProductoListener(this::loadProductos); // Callback para refrescar la lista

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nuevo Producto");
        stage.initModality(Modality.APPLICATION_MODAL); // Para que sea modal
        stage.show();
    }


}