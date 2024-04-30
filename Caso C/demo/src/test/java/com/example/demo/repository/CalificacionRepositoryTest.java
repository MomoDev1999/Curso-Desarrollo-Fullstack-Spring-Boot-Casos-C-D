package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Calificacion;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CalificacionRepositoryTest {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Test
    public void testSaveCalificacion() {
        // Arrange
        Calificacion calificacion = new Calificacion(0, 4, 123, "Usuario1", "2024-04-29");

        // Act
        Calificacion resultado = calificacionRepository.save(calificacion);

        // Assert
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getFecha());
        assertEquals(4, resultado.getCalificacion());
        assertEquals(123, resultado.getIdPublicacion());
        assertEquals("Usuario1", resultado.getRealizadaPor());
        assertEquals("2024-04-29", resultado.getFecha());
    }
}
