package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Publicacion;
import com.example.demo.service.PublicacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PublicacionController.class)
public class PublicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PublicacionService publicacionService;

    @Test
    public void testGetAllPublicaciones() throws Exception {
        // Arrange
        Publicacion publicacion1 = new Publicacion(1, "Título 1", "Autor 1", "2024-05-04", "Descripción 1");
        Publicacion publicacion2 = new Publicacion(2, "Título 2", "Autor 2", "2024-05-05", "Descripción 2");
        List<Publicacion> publicaciones = new ArrayList<>();
        publicaciones.add(publicacion1);
        publicaciones.add(publicacion2);
        when(publicacionService.getAllPublicaciones()).thenReturn(publicaciones);

        // Act & Assert
        mockMvc.perform(get("/publicaciones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testGetPublicacionById() throws Exception {
        // Arrange
        Publicacion publicacion = new Publicacion(1, "Título", "Autor", "2024-05-04", "Descripción");
        when(publicacionService.getPublicacionById(anyInt())).thenReturn(Optional.of(publicacion));

        // Act & Assert
        mockMvc.perform(get("/publicaciones/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testCreatePublicacion() throws Exception {
        // Arrange
        Publicacion publicacion = new Publicacion(1, "Título", "Autor", "2024-05-04", "Descripción");
        when(publicacionService.savePublicacion(any(Publicacion.class))).thenReturn(publicacion);

        // Act & Assert
        mockMvc.perform(post("/publicaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicacion)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testUpdatePublicacion() throws Exception {
        // Arrange
        Publicacion publicacion = new Publicacion(1, "Título", "Autor", "2024-05-04", "Descripción");
        when(publicacionService.getPublicacionById(anyInt())).thenReturn(Optional.of(publicacion));
        when(publicacionService.savePublicacion(any(Publicacion.class))).thenReturn(publicacion);

        // Act & Assert
        mockMvc.perform(put("/publicaciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicacion)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testDeletePublicacion() throws Exception {
        // Arrange
        when(publicacionService.deletePublicacion(anyInt())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/publicaciones/1"))
                .andExpect(status().isNoContent());
    }
}
