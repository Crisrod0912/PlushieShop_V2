package com.PlushieShop.PlushiePro.service.impl;

import com.PlushieShop.PlushiePro.dao.ProductoDao;
import com.PlushieShop.PlushiePro.domain.Producto;
import com.PlushieShop.PlushiePro.service.ProductoService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GET_PRODUCTOS")
                .returningResultSet("p_resultado", BeanPropertyRowMapper.newInstance(Producto.class));

        Map<String, Object> out = simpleJdbcCall.execute();

        var lista = (List<Producto>) out.get("p_resultado");

        if (activo) {
            lista.removeIf(e -> !e.isActivo());
        }

        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    @Transactional
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombreOrderByNombre(String nombre) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("FIND_PRODUCTO_BY_NOMBRE")
                .returningResultSet("p_resultado", BeanPropertyRowMapper.newInstance(Producto.class));

        Map<String, Object> out = simpleJdbcCall.execute(Map.of("p_nombre", nombre));

        List<Producto> productos = (List<Producto>) out.get("p_resultado");

        return productos;
    }

    @Override
    public List<Producto> getProductosByCategoria(Long idCategoria) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("FIND_PRODUCTO_BY_CAT")
                .returningResultSet("p_resultado", BeanPropertyRowMapper.newInstance(Producto.class));

        Map<String, Object> out = simpleJdbcCall.execute(Map.of("cat_id", idCategoria));

        List<Producto> productos = (List<Producto>) out.get("p_resultado");

        return productos;
    }
}
