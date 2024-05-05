package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoService productoService;

    @Test
    public void testGetAllProductos() throws Exception {
        // Arrange
        Producto producto1 = new Producto(1, "Producto 1", 100.0);
        Producto producto2 = new Producto(2, "Producto 2", 200.0);
        List<Producto> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);
        when(productoService.getAllProductos()).thenReturn(productos);

        // Act & Assert
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testGetProductoById() throws Exception {
        // Arrange
        Producto producto = new Producto(1, "Producto 1", 100.0);
        when(productoService.getProductoById(anyInt())).thenReturn(Optional.of(producto));

        // Act & Assert
        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testCreateProducto() throws Exception {
        // Arrange
        Producto producto = new Producto(1, "Producto 1", 100.0);
        when(productoService.saveProducto(any(Producto.class))).thenReturn(producto);

        // Act & Assert
        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testUpdateProducto() throws Exception {
        // Arrange
        Producto producto = new Producto(1, "Producto 1", 100.0);
        when(productoService.getProductoById(anyInt())).thenReturn(Optional.of(producto));
        when(productoService.saveProducto(any(Producto.class))).thenReturn(producto);

        // Act & Assert
        mockMvc.perform(put("/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    public void testDeleteProducto() throws Exception {
        // Arrange
        when(productoService.deleteProducto(anyInt())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isNoContent());
    }
}
