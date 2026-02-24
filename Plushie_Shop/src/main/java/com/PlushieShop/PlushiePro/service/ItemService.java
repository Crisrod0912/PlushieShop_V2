package com.PlushieShop.PlushiePro.service;

import com.PlushieShop.PlushiePro.domain.Item;
import java.util.ArrayList;
import java.util.List;

public interface ItemService {

    List<Item> listaItems = new ArrayList<>();

    public List<Item> gets();

    public Item get(Item item);

    public void delete(Item item);

    public void save(Item item);

    public void actualiza(Item item);

    public void facturar();
}
