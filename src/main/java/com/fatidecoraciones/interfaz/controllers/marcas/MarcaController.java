package com.fatidecoraciones.interfaz.controllers.marcas;

import com.fatidecoraciones.interfaz.models.Marca;
import com.fatidecoraciones.interfaz.services.MarcaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class MarcaController {
    private final MarcaService marcaService = new MarcaService();
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn marcaColumn;
    @FXML
    private TableColumn accionesColumn;
    @FXML
    private TableView marcaTableView;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        // Configurar la columna para que tenga dos botones: Modificar y Eliminar
        accionesColumn.setCellFactory(new Callback<TableColumn<Marca, Void>, TableCell<Marca, Void>>() {
            @Override
            public TableCell<Marca, Void> call(TableColumn<Marca, Void> param) {
                return new TableCell<Marca, Void>() {
                    private final Button btnModificar = new Button("Modificar");
                    private final Button btnEliminar = new Button("Eliminar");
                    private final HBox hbox = new HBox(10); // Espaciado de 10px entre los botones

                    {
                        hbox.getChildren().addAll(btnModificar, btnEliminar);

                        // Acción para el botón de Modificar
                        btnModificar.setOnAction(event -> {
                            Marca marca = getTableView().getItems().get(getIndex());
                            editarMarca(marca); // Llama a editarMarca con la marca seleccionada
                        });

                        // Acción para el botón de Eliminar
                        btnEliminar.setOnAction(event -> {
                            Marca marca = getTableView().getItems().get(getIndex());
                            eliminarMarca(marca);  // Llama a eliminarMarca con la marca seleccionado
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
        loadMarcas();
    }

    private void loadMarcas() {
        try {
            ObservableList<Marca> marcas = FXCollections.observableArrayList(marcaService.getMarcas());
            marcaTableView.setItems(marcas);
        } catch (Exception e) {
            e.printStackTrace();
            // Aquí podrías mostrar un mensaje de error en la UI
        }
    }

    private void eliminarMarca(Marca marca) {
        try {
            marcaService.borrar(marca.getId());
            marcaTableView.getItems().remove(marca);
        } catch (Exception e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    private void editarMarca(Marca marca) {
        try {
            // Cargar el archivo FXML para la ventana de edición
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/View/marcas/EditarMarca.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de edición
            EditarMarcaController editarMarcaController = loader.getController();
            editarMarcaController.setMarca(marca); // Pasar el producto seleccionado a la ventana de edición

            // Crear una nueva escena para el diálogo de edición
            Scene dialogScene = new Scene(root);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Producto");
            dialogStage.setScene(dialogScene);
            dialogStage.showAndWait(); // Esperar a que se cierre el diálogo
            loadMarcas(); // Volver a cargar los productos para reflejar los cambios
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nuevaMarca(ActionEvent actionEvent) throws Exception {
        // Llamar a la ventana de edición con un producto vacío
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/View/marcas/NuevaMarca.fxml"));
        Parent root = loader.load();

        NuevaMarcaController nuevaMarcaController = loader.getController();
        nuevaMarcaController.setMarcaListener(this::loadMarcas); // Callback para refrescar la lista

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nueva Marca");
        stage.initModality(Modality.APPLICATION_MODAL); // Para que sea modal
        stage.show();
    }

}
