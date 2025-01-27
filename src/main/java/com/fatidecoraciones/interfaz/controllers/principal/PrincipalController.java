package com.fatidecoraciones.interfaz.controllers.principal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class PrincipalController {
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab1;
    @FXML
    private Tab tab2;

    @FXML
    private void initialize() {
        try {
            // Cargar el contenido de cada tab
            loadTabContent(tab1, "/com/fatidecoraciones/interfaz/Producto.fxml");
            loadTabContent(tab2, "/com/fatidecoraciones/interfaz/Cotizar.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTabContent(Tab tab, String fxmlFile) throws IOException {
        // Cargar el archivo FXML correspondiente al Tab
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        // Establecer el contenido del Tab
        tab.setContent(root);
    }

   @FXML
    private void irProductos() {
        try {
            // Cargar la ventana 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/Producto.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana 1");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void irCotizar() {
        try {
            // Cargar la ventana 2
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/Cotizar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ventana 2");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}