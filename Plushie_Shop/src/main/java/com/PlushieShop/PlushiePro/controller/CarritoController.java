package com.PlushieShop.PlushiePro.controller;

import com.PlushieShop.PlushiePro.domain.Producto;
import com.PlushieShop.PlushiePro.domain.Item;
import com.PlushieShop.PlushiePro.service.ItemService;
import com.PlushieShop.PlushiePro.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarritoController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    private String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "/index";
    }

    @GetMapping("/carrito/listado")
    public String inicio(Model model) {
        var items = itemService.gets();
        model.addAttribute("items", items);
        var carritoTotalVenta = 0;
        for (Item i : items) {
            carritoTotalVenta += (i.getCantidad() * i.getPrecio());
        }
        model.addAttribute("carritoTotal",
                carritoTotalVenta);
        return "/carrito/listado";
    }

    @GetMapping("/carrito/agregar/{idProducto}")
    public ModelAndView agregarItem(Model model, Item item) {
        Item item2 = itemService.get(item);
        if (item2 == null) {
            Producto producto = productoService.getProducto(item);
            item2 = new Item(producto);
        }
        itemService.save(item2);
        var lista = itemService.gets();
        var totalCarritos = 0;
        var carritoTotalVenta = 0;
        for (Item i : lista) {
            totalCarritos += i.getCantidad();
            carritoTotalVenta += (i.getCantidad() * i.getPrecio());
        }
        model.addAttribute("listaItems", lista);
        model.addAttribute("listaTotal", totalCarritos);
        model.addAttribute("carritoTotal", carritoTotalVenta);
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }

    @GetMapping("/carrito/modificar/{idProducto}")
    public String modificarItem(Item item, Model model) {
        item = itemService.get(item);
        model.addAttribute("item", item);
        return "/carrito/modificar";
    }

    @GetMapping("/carrito/eliminar/{idProducto}")
    public String eliminarItem(Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    @PostMapping("/carrito/guardar")
    public String guardarItem(Item item) {
        itemService.actualiza(item);
        return "redirect:/carrito/listado";
    }

    @GetMapping("/facturar/carrito")
    public String facturarCarrito() {
        itemService.facturar();
        return "redirect:/";
    }
}
