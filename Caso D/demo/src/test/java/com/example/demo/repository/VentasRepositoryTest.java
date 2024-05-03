package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Ventas;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VentasRepositoryTest {

    @Autowired
    private VentasRepository ventasRepository;

    @Test
    public void testSaveVentas() {
        // Arrange
        Ventas ventas = new Ventas();
        ventas.setCantidad(10);
        ventas.setTotal(1000.0);
        ventas.setFecha(Date.valueOf("2022-01-01"));
        ventas.setIdProducto(1);
        ventas.setIdVendedor(1);

        // Act
        Ventas resultado = ventasRepository.save(ventas);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals(10, resultado.getCantidad());
        assertEquals(1000.0, resultado.getTotal(), 0.01);
        assertEquals(1, resultado.getIdProducto());
        assertEquals(1, resultado.getIdVendedor());
    }

}
