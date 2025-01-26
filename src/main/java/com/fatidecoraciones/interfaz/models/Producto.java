package com.fatidecoraciones.interfaz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Producto {

    private Long id;
    private String art;
    private String nombre;
    private double precio;
    private Boolean esTela;
    private Marca marca;

    public Producto() {
    }

    public Producto(Long id, String art, String nombre, double precio, Boolean esTela, Marca marca) {
        this.id = null;
        this.art = art;
        this.nombre = nombre;
        this.precio = precio;
        this.esTela = false;
        this.marca = marca;
    }
}