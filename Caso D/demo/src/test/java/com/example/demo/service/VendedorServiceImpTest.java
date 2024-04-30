package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Vendedor;
import com.example.demo.repository.VendedorRepository;

@ExtendWith(MockitoExtension.class)
public class VendedorServiceImpTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @InjectMocks
    private VendedorServiceImpl vendedorService;

    @Test
    public void testSaveVendedor() {
        // Arrange
        Vendedor vendedor = new Vendedor("Nombre", "Apellido", "12345678-9", 30);
        when(vendedorRepository.save(vendedor)).thenReturn(vendedor);

        // Act
        Vendedor resultado = vendedorService.saveVendedor(vendedor);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nombre", resultado.getNombre());
        assertEquals("Apellido", resultado.getApellido());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals(30, resultado.getEdad());
    }

    @Test
    public void testGetVendedorById() {
        // Arrange
        Vendedor vendedor = new Vendedor("Nombre", "Apellido", "12345678-9", 30);
        Optional<Vendedor> optionalVendedor = Optional.of(vendedor);
        when(vendedorRepository.findById(1)).thenReturn(optionalVendedor);

        // Act
        Optional<Vendedor> resultado = vendedorService.getVendedorById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nombre", resultado.get().getNombre());
        assertEquals("Apellido", resultado.get().getApellido());
        assertEquals("12345678-9", resultado.get().getRut());
        assertEquals(30, resultado.get().getEdad());
    }

    @Test
    public void testDeleteVendedor() {
        // Arrange
        int id = 1;
        when(vendedorRepository.existsById(id)).thenReturn(true);

        // Act
        boolean resultado = vendedorService.deleteVendedor(id);

        // Assert
        assertEquals(true, resultado);
    }
}
