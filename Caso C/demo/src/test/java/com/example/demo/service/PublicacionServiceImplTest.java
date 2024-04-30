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

import com.example.demo.model.Publicacion;
import com.example.demo.repository.PublicacionRepository;

@ExtendWith(MockitoExtension.class)
public class PublicacionServiceImplTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private PublicacionServiceImpl publicacionService;

    @Test
    public void testGetAllPublicaciones() {
        // Arrange
        List<Publicacion> publicaciones = new ArrayList<>();
        publicaciones.add(new Publicacion(1, "Titulo1", "Autor1", "2024-04-29", "Descripcion1"));
        publicaciones.add(new Publicacion(2, "Titulo2", "Autor2", "2024-04-29", "Descripcion2"));
        when(publicacionRepository.findAll()).thenReturn(publicaciones);

        // Act
        List<Publicacion> resultado = publicacionService.getAllPublicaciones();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testSavePublicacion() {
        // Arrange
        Publicacion publicacion = new Publicacion(1, "Titulo1", "Autor1", "2024-04-29", "Descripcion1");
        when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

        // Act
        Publicacion resultado = publicacionService.savePublicacion(publicacion);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Titulo1", resultado.getTitulo());
        assertEquals("Autor1", resultado.getAutor());
        assertEquals("2024-04-29", resultado.getFecha());
        assertEquals("Descripcion1", resultado.getDescripcion());
    }

    @Test
    public void testGetPublicacionById() {
        // Arrange
        Publicacion publicacion = new Publicacion(1, "Titulo1", "Autor1", "2024-04-29", "Descripcion1");
        Optional<Publicacion> optionalPublicacion = Optional.of(publicacion);
        when(publicacionRepository.findById(1)).thenReturn(optionalPublicacion);

        // Act
        Optional<Publicacion> resultado = publicacionService.getPublicacionById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.get().getId());
        assertEquals("Titulo1", resultado.get().getTitulo());
        assertEquals("Autor1", resultado.get().getAutor());
        assertEquals("2024-04-29", resultado.get().getFecha());
        assertEquals("Descripcion1", resultado.get().getDescripcion());
    }

    @Test
    public void testDeletePublicacion_ExistingId() {
        // Arrange
        int id = 1;
        when(publicacionRepository.findById(id)).thenReturn(Optional.of(new Publicacion()));

        // Act
        boolean resultado = publicacionService.deletePublicacion(id);

        // Assert
        assertEquals(true, resultado);
        verify(publicacionRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletePublicacion_NonExistingId() {
        // Arrange
        int id = 1;
        when(publicacionRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean resultado = publicacionService.deletePublicacion(id);

        // Assert
        assertEquals(false, resultado);
        verify(publicacionRepository, never()).deleteById(id);
    }
}
