package com.PlushieShop.PlushiePro.service;

import com.PlushieShop.PlushiePro.domain.Categoria;
import java.util.List;

public interface CategoriaService {

    public List<Categoria> getCategorias();

    public Categoria getCategoria(Categoria categoria);
}
