package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Ventas;
import com.example.demo.repository.VentasRepository;

@ExtendWith(MockitoExtension.class)
public class VentasServiceImplTest {

    @Mock
    private VentasRepository ventasRepository;

    @InjectMocks
    private VentasServiceImpl ventasService;

    @Test
    public void testGetAllVentas() {
        // Arrange
        List<Ventas> ventasList = new ArrayList<>();
        ventasList.add(new Ventas(1, 1, 10.0, new Date(System.currentTimeMillis()), 1, 1));
        ventasList.add(new Ventas(2, 2, 20.0, new Date(System.currentTimeMillis()), 2, 2));
        when(ventasRepository.findAll()).thenReturn(ventasList);

        // Act
        List<Ventas> resultado = ventasService.getAllVentas();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testSaveVentas() {
        // Arrange
        Ventas ventas = new Ventas(1, 1, 10.0, new Date(System.currentTimeMillis()), 1, 1);
        when(ventasRepository.save(ventas)).thenReturn(ventas);

        // Act
        Ventas resultado = ventasService.saveVentas(ventas);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(10.0, resultado.getTotal());
    }

    @Test
    public void testGetVentasById() {
        // Arrange
        Ventas ventas = new Ventas(1, 1, 10.0, new Date(System.currentTimeMillis()), 1, 1);
        Optional<Ventas> optionalVentas = Optional.of(ventas);
        when(ventasRepository.findById(1)).thenReturn(optionalVentas);

        // Act
        Optional<Ventas> resultado = ventasService.getVentasById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.get().getId());
        assertEquals(10.0, resultado.get().getTotal());
    }

    @Test
    public void testDeleteVentas() {
        // Arrange
        int id = 1;
        when(ventasRepository.existsById(id)).thenReturn(true);

        // Act
        boolean resultado = ventasService.deleteVentas(id);

        // Assert
        assertEquals(true, resultado);
    }
}
