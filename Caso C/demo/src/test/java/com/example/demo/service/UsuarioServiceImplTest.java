package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    public void testGetAllUsuarios() {
        // Arrange
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("username1", "Nombre1", "Apellido1", 30));
        usuarios.add(new Usuario("username2", "Nombre2", "Apellido2", 35));
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Act
        List<Usuario> resultado = usuarioService.getAllUsuarios();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testSaveUsuario() {
        // Arrange
        Usuario usuario = new Usuario("username1", "Nombre1", "Apellido1", 30);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        Usuario resultado = usuarioService.saveUsuario(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("username1", resultado.getUsername());
        assertEquals("Nombre1", resultado.getNombre());
        assertEquals("Apellido1", resultado.getApellido());
        assertEquals(30, resultado.getEdad());
    }

    @Test
    public void testGetUsuarioByUsername() {
        // Arrange
        Usuario usuario = new Usuario("username1", "Nombre1", "Apellido1", 30);
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        when(usuarioRepository.findById("username1")).thenReturn(optionalUsuario);

        // Act
        Optional<Usuario> resultado = usuarioService.getUsuarioByUsername("username1");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
        assertEquals("username1", resultado.get().getUsername());
        assertEquals("Nombre1", resultado.get().getNombre());
        assertEquals("Apellido1", resultado.get().getApellido());
        assertEquals(30, resultado.get().getEdad());
    }

    @Test
    public void testDeleteUsuario_ExistingUsername() {
        // Arrange
        String username = "username1";
        when(usuarioRepository.existsById(username)).thenReturn(true);

        // Act
        boolean resultado = usuarioService.deleteUsuario(username);

        // Assert
        assertTrue(resultado);
        verify(usuarioRepository, times(1)).deleteById(username);
    }

    @Test
    public void testDeleteUsuario_NonExistingUsername() {
        // Arrange
        String username = "username1";
        when(usuarioRepository.existsById(username)).thenReturn(false);

        // Act
        boolean resultado = usuarioService.deleteUsuario(username);

        // Assert
        assertFalse(resultado);
        verify(usuarioRepository, never()).deleteById(username);
    }
}
