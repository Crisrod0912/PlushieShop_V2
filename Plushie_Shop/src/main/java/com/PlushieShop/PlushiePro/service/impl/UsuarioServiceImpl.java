package com.PlushieShop.PlushiePro.service.impl;

import com.PlushieShop.PlushiePro.dao.UsuarioDao;
import com.PlushieShop.PlushiePro.domain.Usuario;
import com.PlushieShop.PlushiePro.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("GET_USU_USER")
                .returningResultSet("CDATOS", (rs, rowNum) -> {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getLong("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellidos(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setDireccion(rs.getString("direccion"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setTarjeta(rs.getString("tarjeta"));
                    usuario.setPin(rs.getString("pin"));
                    usuario.setFecha(rs.getDate("fecha"));
                    usuario.setActivo(rs.getBoolean("activo"));
                    usuario.setRol(rs.getInt("id_rol"));
                    return usuario;
                });

        Map<String, Object> out = simpleJdbcCall.execute(Map.of("user_name", username));

        return ((List<Usuario>) out.get("CDATOS")).stream().findFirst().orElse(null);

    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameYPassword(String username, String password) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("GET_USU_USER_PASS")
                .returningResultSet("CDATOS", (rs, rowNum) -> {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getLong("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellidos(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setDireccion(rs.getString("direccion"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setTarjeta(rs.getString("tarjeta"));
                    usuario.setPin(rs.getString("pin"));
                    usuario.setFecha(rs.getDate("fecha"));
                    usuario.setActivo(rs.getBoolean("activo"));
                    usuario.setRol(rs.getInt("id_rol"));
                    return usuario;
                });

        Map<String, Object> out = simpleJdbcCall.execute(Map.of("user_name", username, "pass", password));

        return ((List<Usuario>) out.get("CDATOS")).stream().findFirst().orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameOCorreo(String username, String correo) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("GET_USU_USER_CORRE")
                .returningResultSet("CDATOS", (rs, rowNum) -> {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getLong("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellidos(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setDireccion(rs.getString("direccion"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setTarjeta(rs.getString("tarjeta"));
                    usuario.setPin(rs.getString("pin"));
                    usuario.setFecha(rs.getDate("fecha"));
                    usuario.setActivo(rs.getBoolean("activo"));
                    usuario.setRol(rs.getInt("id_rol"));
                    return usuario;
                });

        Map<String, Object> out = simpleJdbcCall.execute(Map.of("user_name", username, "corre", correo));

        return ((List<Usuario>) out.get("CDATOS")).stream().findFirst().orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsuarioPorUsernameOCorreo(String username, String correo) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("EXISTE_USU");
        Map<String, Object> retorno = simpleJdbcCall.execute(Map.of("user_name", username,
                "correo_user", correo));

        Number resultado = (Number) retorno.get("return");

        int result = resultado.intValue();

        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional
    public void crear_user(Usuario usuario) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("CREAR_USUARIO");
        SqlParameterSource consulta = new MapSqlParameterSource()
                .addValue("nomb", usuario.getNombre())
                .addValue("apelli", usuario.getApellidos())
                .addValue("mail", usuario.getCorreo())
                .addValue("direc", usuario.getDireccion())
                .addValue("userna", usuario.getUsername())
                .addValue("pass", usuario.getPassword())
                .addValue("tarj", usuario.getTarjeta())
                .addValue("pin_us", usuario.getPin());

        simpleJdbcCall.execute(consulta);
    }

    @Override
    @Transactional
    public void actualizar_user(Usuario usuario) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ACTUALIZAR_USUARIO");
        SqlParameterSource consulta = new MapSqlParameterSource()
                .addValue("usuario_id", usuario.getIdUsuario())
                .addValue("nomb", usuario.getNombre())
                .addValue("apelli", usuario.getApellidos())
                .addValue("mail", usuario.getCorreo())
                .addValue("direc", usuario.getDireccion())
                .addValue("userna", usuario.getUsername())
                .addValue("pass", usuario.getPassword())
                .addValue("tarj", usuario.getTarjeta())
                .addValue("pin_us", usuario.getPin());

        simpleJdbcCall.execute(consulta);
    }
}
