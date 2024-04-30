package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Vendedor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VendedorRepositoryTest {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Test
    public void testSaveVendedor() {
        // Arrange
        Vendedor vendedor = new Vendedor();
        vendedor.setNombre("Nombre");
        vendedor.setApellido("Apellido");
        vendedor.setRut("12345678-9");
        vendedor.setEdad(30);

        // Act
        Vendedor resultado = vendedorRepository.save(vendedor);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals("Nombre", resultado.getNombre());
        assertEquals("Apellido", resultado.getApellido());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("30", String.valueOf(resultado.getEdad()));
    }
}
