package com.PlushieShop.PlushiePro.service.impl;

import com.PlushieShop.PlushiePro.dao.CategoriaDao;
import com.PlushieShop.PlushiePro.domain.Categoria;
import com.PlushieShop.PlushiePro.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        var lista = categoriaDao.findAll();

        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {
        return categoriaDao.findById(categoria.getIdCategoria()).orElse(null);
    }
}
