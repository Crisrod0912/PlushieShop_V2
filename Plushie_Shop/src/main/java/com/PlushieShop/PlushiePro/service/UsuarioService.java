package com.PlushieShop.PlushiePro.service;

import com.PlushieShop.PlushiePro.domain.Usuario;

public interface UsuarioService {

    public Usuario getUsuarioPorUsername(String username);

    public Usuario getUsuarioPorUsernameYPassword(String username, String password);

    public Usuario getUsuarioPorUsernameOCorreo(String username, String correo);

    public boolean existeUsuarioPorUsernameOCorreo(String username, String correo);

    public void crear_user(Usuario usuario);

    public void actualizar_user(Usuario usuario);
}
