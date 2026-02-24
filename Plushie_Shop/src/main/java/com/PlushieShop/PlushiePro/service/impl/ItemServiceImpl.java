package com.PlushieShop.PlushiePro.service.impl;

import com.PlushieShop.PlushiePro.dao.FacturaDao;
import com.PlushieShop.PlushiePro.dao.VentaDao;
import com.PlushieShop.PlushiePro.dao.ProductoDao;
import com.PlushieShop.PlushiePro.domain.Usuario;
import com.PlushieShop.PlushiePro.domain.Item;
import com.PlushieShop.PlushiePro.service.UsuarioService;
import com.PlushieShop.PlushiePro.service.ItemService;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import static com.PlushieShop.PlushiePro.service.ItemService.listaItems;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> gets() {
        return listaItems;
    }

    @Override
    public void save(Item item) {
        boolean existe = false;
        for (Item i : listaItems) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                if (i.getCantidad() < item.getExistencias()) {
                    i.setCantidad(i.getCantidad() + 1);
                }
                existe = true;
                break;
            }
        }
        if (!existe) {
            item.setCantidad(1);
            listaItems.add(item);
        }
    }

    @Override
    public void delete(Item item) {
        var posicion = -1;
        var existe = false;
        for (Item i : listaItems) {
            ++posicion;
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                existe = true;
                break;
            }
        }
        if (existe) {
            listaItems.remove(posicion);
        }
    }

    @Override
    public Item get(Item item) {
        for (Item i : listaItems) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void actualiza(Item item) {
        for (Item i : listaItems) {
            if (Objects.equals(i.getIdProducto(), item.getIdProducto())) {
                i.setCantidad(item.getCantidad());
                break;
            }
        }
    }
    @Autowired
    private UsuarioService uuarioService;
    @Autowired
    private FacturaDao facturaDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void facturar() {
        System.out.println("Facturando");
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }
        if (username.isBlank()) {
            return;
        }
        Usuario uuario = uuarioService.getUsuarioPorUsername(username);
        if (uuario == null) {
            return;
        }

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("CREAR_FACTURA");
        Map<String, Object> retorno = simpleJdbcCall.execute(Map.of("usu_id", uuario.getIdUsuario()));

        Number idFacturaNumber = (Number) retorno.get("return");

        Long id_factura = idFacturaNumber.longValue();

        double total = 0;
        for (Item i : listaItems) {
            System.out.println("Producto: " + i.getDescripcion() + " Cantidad: " + i.getCantidad() + " Total: " + i.getPrecio() * i.getCantidad());

            SimpleJdbcCall simpleJdbcCall2 = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("INSERTAR_VENTA");

            SqlParameterSource consulta = new MapSqlParameterSource()
                    .addValue("fac_id", id_factura)
                    .addValue("prod_id", i.getIdProducto())
                    .addValue("prec", i.getPrecio())
                    .addValue("cant", i.getCantidad());

            simpleJdbcCall2.execute(consulta);

            total += i.getPrecio() * i.getCantidad();
        }

        SimpleJdbcCall simpleJdbcCall3 = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ACTUALIZAR_FACTURA");
        SqlParameterSource consulta = new MapSqlParameterSource()
                .addValue("fac_id", id_factura)
                .addValue("tot", total);
        simpleJdbcCall3.execute(consulta);

        listaItems.clear();
    }
}
