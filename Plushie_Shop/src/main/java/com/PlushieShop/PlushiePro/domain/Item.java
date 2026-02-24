package com.PlushieShop.PlushiePro.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Item extends Producto {

    private int cantidad;

    public Item() {
    }

    public Item(Producto producto) {
        super.setIdProducto(producto.getIdProducto());
        super.setNombre(producto.getNombre());
        super.setDescripcion(producto.getDescripcion());
        super.setPrecio(producto.getPrecio());
        super.setExistencias(producto.getExistencias());
        super.setRutaImagen(producto.getRutaImagen());
        super.setActivo(producto.isActivo());

        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
