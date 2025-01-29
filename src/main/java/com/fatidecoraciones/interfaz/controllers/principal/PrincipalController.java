package com.fatidecoraciones.interfaz.controllers.principal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalController {
    @FXML
    private Tab tab1;
    @FXML
    private Tab cotizarSisTab;
    @FXML
    private Tab cotizarTelasTab;

    @FXML
    private void initialize() {
        try {
            // Cargar el contenido de cada tab
            loadTabContent(tab1, "/com/fatidecoraciones/interfaz/Producto.fxml");
            loadTabContent(cotizarSisTab, "/com/fatidecoraciones/interfaz/CotizarSistemas.fxml");
            loadTabContent(cotizarTelasTab, "/com/fatidecoraciones/interfaz/CotizarTelas.fxml");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/Producto.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Telas y Accesorios");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void nuevo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fatidecoraciones/interfaz/NuevoProducto.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Telas y Accesorios");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}