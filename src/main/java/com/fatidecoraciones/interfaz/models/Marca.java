package com.fatidecoraciones.interfaz.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Marca {
    private Long id;
    private String marca;

    public Marca() {
    }

    public Marca(Long id, String marca) {
        this.id = id;
        this.marca = marca;
    }
}
