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

import com.example.demo.model.Comentarios;
import com.example.demo.repository.ComentariosRepository;

@ExtendWith(MockitoExtension.class)
public class ComentariosServiceImplTest {

    @Mock
    private ComentariosRepository comentariosRepository;

    @InjectMocks
    private ComentariosServiceImpl comentariosService;

    @Test
    public void testGetAllComentarios() {
        // Arrange
        List<Comentarios> comentarios = new ArrayList<>();
        comentarios.add(new Comentarios(1, "Comentario1", 1, "Usuario1", "2024-04-29"));
        comentarios.add(new Comentarios(2, "Comentario2", 1, "Usuario2", "2024-04-29"));
        when(comentariosRepository.findAll()).thenReturn(comentarios);

        // Act
        List<Comentarios> resultado = comentariosService.getAllComentarios();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testSaveComentarios() {
        // Arrange
        Comentarios comentario = new Comentarios(1, "Comentario1", 1, "Usuario1", "2024-04-29");
        when(comentariosRepository.save(comentario)).thenReturn(comentario);

        // Act
        Comentarios resultado = comentariosService.saveComentarios(comentario);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Comentario1", resultado.getComentario());
        assertEquals(1, resultado.getIdPublicacion());
        assertEquals("Usuario1", resultado.getRealizadoPor());
        assertEquals("2024-04-29", resultado.getFecha());
    }

    @Test
    public void testGetComentarioById() {
        // Arrange
        Comentarios comentario = new Comentarios(1, "Comentario1", 1, "Usuario1", "2024-04-29");
        Optional<Comentarios> optionalComentario = Optional.of(comentario);
        when(comentariosRepository.findById(1)).thenReturn(optionalComentario);

        // Act
        Optional<Comentarios> resultado = comentariosService.getComentarioById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.get().getId());
        assertEquals("Comentario1", resultado.get().getComentario());
        assertEquals(1, resultado.get().getIdPublicacion());
        assertEquals("Usuario1", resultado.get().getRealizadoPor());
        assertEquals("2024-04-29", resultado.get().getFecha());
    }

    @Test
    public void testDeleteComentario() {
        // Arrange
        int id = 1;
        when(comentariosRepository.existsById(id)).thenReturn(true);

        // Act
        boolean resultado = comentariosService.deleteComentario(id);

        // Assert
        assertEquals(true, resultado);
    }

}
