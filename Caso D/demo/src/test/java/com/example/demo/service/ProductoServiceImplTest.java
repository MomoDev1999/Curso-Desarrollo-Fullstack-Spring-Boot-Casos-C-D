package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    public void testGetAllProductos() {
        // Arrange
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Producto1", 10.0));
        productos.add(new Producto("Producto2", 20.0));
        when(productoRepository.findAll()).thenReturn(productos);

        // Act
        List<Producto> resultado = productoService.getAllProductos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testSaveProducto() {
        // Arrange
        Producto producto = new Producto("Producto1", 10.0);
        when(productoRepository.save(producto)).thenReturn(producto);

        // Act
        Producto resultado = productoService.saveProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Producto1", resultado.getNombre());
        assertEquals(10.0, resultado.getPrecio());
    }

    @Test
    public void testGetProductoById() {
        // Arrange
        Producto producto = new Producto("Producto1", 10.0);
        Optional<Producto> optionalProducto = Optional.of(producto);
        when(productoRepository.findById(1)).thenReturn(optionalProducto);

        // Act
        Optional<Producto> resultado = productoService.getProductoById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Producto1", resultado.get().getNombre());
        assertEquals(10.0, resultado.get().getPrecio());
    }

    @Test
    public void testDeleteProducto() {
        // Arrange
        int id = 1;
        when(productoRepository.existsById(id)).thenReturn(true);

        // Act
        boolean resultado = productoService.deleteProducto(id);

        // Assert
        assertEquals(true, resultado);
    }
}
