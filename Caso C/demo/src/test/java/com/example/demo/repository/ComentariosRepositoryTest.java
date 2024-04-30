package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Comentarios;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ComentariosRepositoryTest {

    @Autowired
    private ComentariosRepository comentariosRepository;

    @Test
    public void testSaveComentario() {
        // Arrange
        Comentarios comentario = new Comentarios(0, "¡Gran publicación!", 456, "Usuario2", "2024-04-29");

        // Act
        Comentarios resultado = comentariosRepository.save(comentario);

        // Assert
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getFecha());
        assertEquals("¡Gran publicación!", resultado.getComentario());
        assertEquals(456, resultado.getIdPublicacion());
        assertEquals("Usuario2", resultado.getRealizadoPor());
        assertEquals("2024-04-29", resultado.getFecha());
    }
}
