package com.PlushieShop.PlushiePro.dao;

import com.PlushieShop.PlushiePro.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDao extends JpaRepository<Producto, Long> {

    public List<Producto> findByNombreOrderByNombre(String nombre);

    List<Producto> findByIdCategoria(Long idCategoria);
}
