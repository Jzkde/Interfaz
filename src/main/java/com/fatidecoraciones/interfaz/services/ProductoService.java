package com.fatidecoraciones.interfaz.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatidecoraciones.interfaz.models.Producto;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProductoService {
    private static final String API_URL = "http://localhost:8090/prod";
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    public List<Producto> getProductos() throws Exception {

        // Preparar la solicitud HTTP GET
        URL url = new URL(API_URL + "/lista/total");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() == 200) {
            try (InputStream inputStream = connection.getInputStream()) {

                // Deserializar JSON a una lista de objetos
                return objectMapper.readValue(inputStream, new TypeReference<List<Producto>>() {
                });
            }
        } else {
            throw new Exception("Failed to fetch data: HTTP " + connection.getResponseCode());
        }
    }

    public void editar(Producto producto, String marca) throws Exception {
        // Codificar el nombre de la marca
        String marcaId = URLEncoder.encode(marca, StandardCharsets.UTF_8.toString());

        // Convertir en JSON
        String jsonProducto = objectMapper.writeValueAsString(producto);

        // Preparar la solicitud HTTP PUT
        URL url = new URL(API_URL + "/editar/" + producto.getId() + "?marcaId=" + marcaId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Escribir el cuerpo de la solicitud
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonProducto.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        // Obtener el código de respuesta y manejarlo
        if (connection.getResponseCode() != 200) {
            throw new Exception("Failed to update product: HTTP " + connection.getResponseCode());
        }
    }

    public void nuevo(Producto producto, String marca) throws Exception {
        // Codificar el nombre de la marca
        String marcaId = URLEncoder.encode(marca, StandardCharsets.UTF_8.toString());

        // Convertiren JSON
        String jsonProducto = objectMapper.writeValueAsString(producto);

        // Preparar la solicitud HTTP POST
        URL url = new URL(API_URL + "/nuevo?marcaN=" + marcaId);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Escribir el cuerpo de la solicitu
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonProducto.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        // Obtener el código de respuesta y manejarlo
        if (connection.getResponseCode() != 200 && connection.getResponseCode() != 201) {
            throw new Exception("Failed to update product: HTTP " + connection.getResponseCode());
        }
    }

    public void borrar(Long id) throws Exception {
        URL url = new URL(API_URL + "/borrar/" + id);

        // Preparar la solicitud HTTP DELETE
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Accept", "application/json");

        // Verificar si la eliminación fue exitosa
        if (connection.getResponseCode() != 200 && connection.getResponseCode() != 201) {
            throw new Exception("Failed to delete product: HTTP " + connection.getResponseCode());
        }
    }

    public void cotizar(List<Producto> productos, String tela, Float ancho, Float prop, String tipoConf) throws Exception {
        // Codificar el nombre de la marca
        String telaN = URLEncoder.encode(tela, StandardCharsets.UTF_8.toString());

        URL url = new URL(API_URL + "/cotizar?telaN=" + telaN + "&ancho=" + ancho + "&prop=" + prop + "&tipoConf=" + tipoConf);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        // Convertiren JSON
        String jsonProducto = objectMapper.writeValueAsString(productos);

        // Escribir el cuerpo de la solicitud
        try (OutputStream outputStream = conn.getOutputStream()) {
            byte[] input = jsonProducto.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        // Leer la respuesta del servidor
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) { // Si es exitoso
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = reader.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Convertir la respuesta a un Double (el total que devuelve el servidor)
                Double total = Double.valueOf(response.toString());

                // Mostrar el total en la interfaz gráfica
                mostrarTotal(total);
            }
        } else {
            throw new Exception("Error al cotizar: HTTP " + responseCode);
        }
    }

    private void mostrarTotal(Double total) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cotización");
            alert.setHeaderText("Total de la cotización");
            alert.setContentText("El total es: $" + total);
            alert.showAndWait();
        });
    }

}
