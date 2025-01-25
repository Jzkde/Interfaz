package com.fatidecoraciones.interfaz.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatidecoraciones.interfaz.models.Marca;
import com.fatidecoraciones.interfaz.models.Producto;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ProductoService {
    private static final String API_URL = "http://localhost:8090/prod";
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    public List<Producto> getProductos() throws Exception {
        URL url = new URL(API_URL+"/lista/total");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() == 200) {
            try (InputStream inputStream = connection.getInputStream()) {
                // Deserializar JSON a una lista de objetos Post
                return objectMapper.readValue(inputStream, new TypeReference<List<Producto>>() {});
            }
        } else {
            throw new Exception("Failed to fetch data: HTTP " + connection.getResponseCode());
        }
    }
}