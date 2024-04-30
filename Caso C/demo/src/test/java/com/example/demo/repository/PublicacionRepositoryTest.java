package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Publicacion;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PublicacionRepositoryTest {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Test
    public void testSavePublicacion() {
        // Arrange
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo("Título de la publicación");
        publicacion.setAutor("Autor1");
        publicacion.setFecha("2024-04-29");
        publicacion.setDescripcion("Descripción de la publicación");

        // Act
        Publicacion resultado = publicacionRepository.save(publicacion);

        // Assert
        assertNotNull(resultado.getId());
        assertEquals(1, resultado.getId());
        assertEquals("Título de la publicación", resultado.getTitulo());
        assertEquals("Autor1", resultado.getAutor());
        assertEquals("2024-04-29", resultado.getFecha());
        assertEquals("Descripción de la publicación", resultado.getDescripcion());
    }
}
