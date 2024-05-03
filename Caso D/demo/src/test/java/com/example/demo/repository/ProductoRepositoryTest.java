package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Producto;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    public void testSaveProducto() {
        // Arrange
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto de Prueba");
        producto.setPrecio(50.0);

        // Act
        Producto resultado = productoRepository.save(producto);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals(1, resultado.getId());
        assertEquals("Producto de Prueba", resultado.getNombre());
        assertEquals("50.0", String.valueOf(resultado.getPrecio()));
    }
}
