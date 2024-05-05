package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Calificacion;
import com.example.demo.service.CalificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalificacionController.class)
public class CalificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalificacionService calificacionService;

    @Test
    public void testGetAllCalificaciones() throws Exception {
        // Arrange
        Calificacion calificacion1 = new Calificacion(1, 4, 1, "Usuario1", "2024-05-04");
        Calificacion calificacion2 = new Calificacion(2, 5, 1, "Usuario2", "2024-05-04");
        List<Calificacion> calificaciones = new ArrayList<>();
        calificaciones.add(calificacion1);
        calificaciones.add(calificacion2);
        when(calificacionService.getAllCalificaciones()).thenReturn(calificaciones);

        // Act & Assert
        mockMvc.perform(get("/calificaciones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testGetCalificacionById() throws Exception {
        // Arrange
        Calificacion calificacion = new Calificacion(1, 4, 1, "Usuario1", "2024-05-04");
        when(calificacionService.getCalificacionById(anyInt())).thenReturn(Optional.of(calificacion));

        // Act & Assert
        mockMvc.perform(get("/calificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testCreateCalificacion() throws Exception {
        // Arrange
        Calificacion calificacion = new Calificacion(1, 4, 1, "Usuario1", "2024-05-04");
        when(calificacionService.saveCalificacion(any(Calificacion.class))).thenReturn(calificacion);

        // Act & Assert
        mockMvc.perform(post("/calificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calificacion)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testUpdateCalificacion() throws Exception {
        // Arrange
        Calificacion calificacion = new Calificacion(1, 4, 1, "Usuario1", "2024-05-04");
        when(calificacionService.getCalificacionById(anyInt())).thenReturn(Optional.of(calificacion));
        when(calificacionService.saveCalificacion(any(Calificacion.class))).thenReturn(calificacion);

        // Act & Assert
        mockMvc.perform(put("/calificaciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calificacion)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testDeleteCalificacion() throws Exception {
        // Arrange
        when(calificacionService.deleteCalificacion(anyInt())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/calificaciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetPromedioCalificacionesByPublicacionId() throws Exception {
        // Arrange
        double promedio = 4.5;
        when(calificacionService.getPromedioCalificacionesByPublicacionId(anyInt())).thenReturn(promedio);

        // Act & Assert
        mockMvc.perform(get("/calificaciones/promedio/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(promedio));
    }
}
