package com.PlushieShop.PlushiePro.service.impl;

import com.PlushieShop.PlushiePro.domain.Usuario;
import com.PlushieShop.PlushiePro.service.UsuarioService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service("userDetailsService")
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getUsuarioPorUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        String rol = (usuario.getRol() == 1) ? "ROLE_ADMIN" : "ROLE_USER";
        var roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(rol));

        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
