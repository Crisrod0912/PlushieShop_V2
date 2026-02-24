package com.PlushieShop.PlushiePro.service;

import com.PlushieShop.PlushiePro.domain.Producto;
import java.util.List;

public interface ProductoService {

    public List<Producto> getProductos(boolean activo);

    public Producto getProducto(Producto producto);

    public void save(Producto producto);

    public void delete(Producto producto);

    public List<Producto> findByNombreOrderByNombre(String nombre);

    public List<Producto> getProductosByCategoria(Long idCategoria);
}
