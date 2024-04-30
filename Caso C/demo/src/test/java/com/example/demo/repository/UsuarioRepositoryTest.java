package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Usuario;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testSaveUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario1");
        usuario.setNombre("NombreUsuario");
        usuario.setApellido("ApellidoUsuario");
        usuario.setEdad(30);

        // Act
        Usuario resultado = usuarioRepository.save(usuario);

        // Assert
        assertNotNull(resultado.getUsername());
        assertEquals("usuario1", resultado.getUsername());
        assertEquals("NombreUsuario", resultado.getNombre());
        assertEquals("ApellidoUsuario", resultado.getApellido());
        assertEquals(30, resultado.getEdad());
    }
}
