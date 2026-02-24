package com.PlushieShop.PlushiePro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PlushieShop.PlushiePro.domain.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Usuario findByUsernameAndPassword(String username, String Password);

    Usuario findByUsernameOrCorreo(String username, String Correo);

    boolean existsByUsernameOrCorreo(String username, String Correo);
}
