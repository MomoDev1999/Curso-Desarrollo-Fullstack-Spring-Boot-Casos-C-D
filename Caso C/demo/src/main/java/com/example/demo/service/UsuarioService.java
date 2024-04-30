// UsuarioService.java
package com.example.demo.service;

import com.example.demo.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();

    Usuario saveUsuario(Usuario usuario);

    Optional<Usuario> getUsuarioByUsername(String username);

    boolean deleteUsuario(String username);
}
