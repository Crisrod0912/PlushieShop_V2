package com.PlushieShop.PlushiePro.service.impl;

import com.PlushieShop.PlushiePro.dao.FacturaDao;
import com.PlushieShop.PlushiePro.domain.Factura;
import com.PlushieShop.PlushiePro.service.FacturaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaDao facturaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Factura> getFacturas() {
        var lista = facturaDao.findAll();
        return lista;
    }
}
