package com.PlushieShop.PlushiePro.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "categoria")

public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;
    private String nombre;
    private String tipo;
    @Column(name = "activo")
    private int activo;

    public Categoria() {
    }

    public Categoria(String categoria, boolean activo) {
        this.tipo = categoria;
        this.activo = activo ? 1 : 0;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo ? 1 : 0;
    }

    public boolean isActivo() {
        return this.activo == 1;
    }
}
