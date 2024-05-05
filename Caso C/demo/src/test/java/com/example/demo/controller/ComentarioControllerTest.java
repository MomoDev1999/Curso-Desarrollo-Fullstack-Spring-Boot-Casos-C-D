package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Comentarios;
import com.example.demo.service.ComentariosService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ComentarioController.class)
public class ComentarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ComentariosService comentariosService;

    @Test
    public void testGetAllComentarios() throws Exception {
        // Arrange
        Comentarios comentario1 = new Comentarios(1, "Excelente artículo", 1, "Usuario1", "2024-05-04");
        Comentarios comentario2 = new Comentarios(2, "Muy interesante", 1, "Usuario2", "2024-05-04");
        List<Comentarios> comentarios = new ArrayList<>();
        comentarios.add(comentario1);
        comentarios.add(comentario2);
        when(comentariosService.getAllComentarios()).thenReturn(comentarios);

        // Act & Assert
        mockMvc.perform(get("/comentarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testGetComentarioById() throws Exception {
        // Arrange
        Comentarios comentario = new Comentarios(1, "Excelente artículo", 1, "Usuario1", "2024-05-04");
        when(comentariosService.getComentarioById(anyInt())).thenReturn(Optional.of(comentario));

        // Act & Assert
        mockMvc.perform(get("/comentarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testCreateComentario() throws Exception {
        // Arrange
        Comentarios comentario = new Comentarios(1, "Excelente artículo", 1, "Usuario1", "2024-05-04");
        when(comentariosService.saveComentarios(any(Comentarios.class))).thenReturn(comentario);

        // Act & Assert
        mockMvc.perform(post("/comentarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comentario)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testUpdateComentario() throws Exception {
        // Arrange
        Comentarios comentario = new Comentarios(1, "Excelente artículo", 1, "Usuario1", "2024-05-04");
        when(comentariosService.getComentarioById(anyInt())).thenReturn(Optional.of(comentario));
        when(comentariosService.saveComentarios(any(Comentarios.class))).thenReturn(comentario);

        // Act & Assert
        mockMvc.perform(put("/comentarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comentario)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testDeleteComentario() throws Exception {
        // Arrange
        when(comentariosService.deleteComentario(anyInt())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/comentarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetComentariosByPublicacionId() throws Exception {
        // Arrange
        int idPublicacion = 1;
        Comentarios comentario1 = new Comentarios(1, "Excelente artículo", 1, "Usuario1", "2024-05-04");
        Comentarios comentario2 = new Comentarios(2, "Muy interesante", 1, "Usuario2", "2024-05-04");
        List<Comentarios> comentarios = new ArrayList<>();
        comentarios.add(comentario1);
        comentarios.add(comentario2);
        when(comentariosService.getComentariosByPublicacionId(idPublicacion)).thenReturn(comentarios);

        // Act & Assert
        mockMvc.perform(get("/comentarios/publicacion/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }
}
