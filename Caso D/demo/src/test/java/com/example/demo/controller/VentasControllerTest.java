package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;

import com.example.demo.model.Ventas;
import com.example.demo.service.VentasService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VentasController.class)
public class VentasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VentasService ventasService;

    @Test
    public void testGetAllVentas() throws Exception {
        // Arrange
        Ventas ventas1 = new Ventas(1, 10, 1000.0, null, 1, 1);
        Ventas ventas2 = new Ventas(2, 20, 2000.0, null, 2, 2);
        List<Ventas> ventasList = new ArrayList<>();
        ventasList.add(ventas1);
        ventasList.add(ventas2);
        when(ventasService.getAllVentas()).thenReturn(ventasList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/ventas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(HAL_JSON_VALUE)); // Aquí el cambio
    }

    @Test
    public void testGetVentasById() throws Exception {
        // Arrange
        Ventas ventas = new Ventas(1, 10, 1000.0, null, 1, 1);
        when(ventasService.getVentasById(anyInt())).thenReturn(Optional.of(ventas));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/ventas/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(HAL_JSON_VALUE)); // Aquí el cambio
    }

    @Test
    public void testCreateVentas() throws Exception {
        // Arrange
        Ventas ventas = new Ventas(1, 10, 1000.0, null, 1, 1);
        when(ventasService.saveVentas(any(Ventas.class))).thenReturn(ventas);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventas)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(HAL_JSON_VALUE)); // Aquí el cambio
    }

    @Test
    public void testUpdateVentas() throws Exception {
        // Arrange
        Ventas ventas = new Ventas(1, 10, 1000.0, null, 1, 1);
        when(ventasService.getVentasById(anyInt())).thenReturn(Optional.of(ventas));
        when(ventasService.saveVentas(any(Ventas.class))).thenReturn(ventas);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/ventas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventas)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(HAL_JSON_VALUE)); // Aquí el cambio
    }

    @Test
    public void testDeleteVentas() throws Exception {
        // Arrange
        when(ventasService.deleteVentas(anyInt())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/ventas/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
