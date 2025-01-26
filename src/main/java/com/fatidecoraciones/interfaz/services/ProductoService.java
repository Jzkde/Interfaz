package com.fatidecoraciones.interfaz.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatidecoraciones.interfaz.models.Producto;

import java.io.InputStream;
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

        // Convertir el producto a JSON
        String jsonProducto = objectMapper.writeValueAsString(producto);

        // Preparar la solicitud HTTP PUT
        URL url = new URL(API_URL + "/editar/" + producto.getId() + "?marcaId=" + marcaId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Escribir el cuerpo de la solicitud (el producto en formato JSON)
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

        // Convertir el producto a JSON
        String jsonProducto = objectMapper.writeValueAsString(producto);

        // Preparar la solicitud HTTP POST
        URL url = new URL(API_URL + "/nuevo?marcaN=" + marcaId);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Escribir el cuerpo de la solicitud (el producto en formato JSON)
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
}
