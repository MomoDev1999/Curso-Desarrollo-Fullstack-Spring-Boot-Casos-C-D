package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void testGetAllUsuarios() throws Exception {
        // Arrange
        Usuario usuario1 = new Usuario("usuario1", "Nombre1", "Apellido1", 30);
        Usuario usuario2 = new Usuario("usuario2", "Nombre2", "Apellido2", 35);
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        // Act & Assert
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testGetUsuarioByUsername() throws Exception {
        // Arrange
        Usuario usuario = new Usuario("usuario", "Nombre", "Apellido", 30);
        when(usuarioService.getUsuarioByUsername(anyString())).thenReturn(Optional.of(usuario));

        // Act & Assert
        mockMvc.perform(get("/usuarios/usuario"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testCreateUsuario() throws Exception {
        // Arrange
        Usuario usuario = new Usuario("usuario", "Nombre", "Apellido", 30);
        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        // Arrange
        Usuario usuario = new Usuario("usuario", "Nombre", "Apellido", 30);
        when(usuarioService.getUsuarioByUsername(anyString())).thenReturn(Optional.of(usuario));
        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        // Act & Assert
        mockMvc.perform(put("/usuarios/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        // Arrange
        when(usuarioService.deleteUsuario(anyString())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/usuarios/usuario"))
                .andExpect(status().isNoContent());
    }
}
