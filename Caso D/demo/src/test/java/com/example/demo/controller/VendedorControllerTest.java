package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Vendedor;
import com.example.demo.service.VendedorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VendedorController.class)
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VendedorService vendedorService;

    @Test
    public void testGetAllVendedores() throws Exception {
        // Arrange
        Vendedor vendedor1 = new Vendedor("Nombre 1", "Apellido 1", "11111111-1", 30);
        Vendedor vendedor2 = new Vendedor("Nombre 2", "Apellido 2", "22222222-2", 35);
        List<Vendedor> vendedores = new ArrayList<>();
        vendedores.add(vendedor1);
        vendedores.add(vendedor2);
        when(vendedorService.getAllVendedores()).thenReturn(vendedores);

        // Act & Assert
        mockMvc.perform(get("/vendedores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testGetVendedorById() throws Exception {
        // Arrange
        Vendedor vendedor = new Vendedor("Nombre", "Apellido", "11111111-1", 30);
        when(vendedorService.getVendedorById(anyInt())).thenReturn(Optional.of(vendedor));

        // Act & Assert
        mockMvc.perform(get("/vendedores/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testCreateVendedor() throws Exception {
        // Arrange
        Vendedor vendedor = new Vendedor("Nombre", "Apellido", "11111111-1", 30);
        when(vendedorService.saveVendedor(any(Vendedor.class))).thenReturn(vendedor);

        // Act & Assert
        mockMvc.perform(post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendedor)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testUpdateVendedor() throws Exception {
        // Arrange
        Vendedor vendedor = new Vendedor("Nombre", "Apellido", "11111111-1", 30);
        when(vendedorService.getVendedorById(anyInt())).thenReturn(Optional.of(vendedor));
        when(vendedorService.saveVendedor(any(Vendedor.class))).thenReturn(vendedor);

        // Act & Assert
        mockMvc.perform(put("/vendedores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendedor)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testDeleteVendedor() throws Exception {
        // Arrange
        when(vendedorService.deleteVendedor(anyInt())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/vendedores/1"))
                .andExpect(status().isNoContent());
    }
}
