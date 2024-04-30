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

import com.example.demo.model.Calificacion;
import com.example.demo.repository.CalificacionRepository;

@ExtendWith(MockitoExtension.class)
public class CalificacionServiceImplTest {

    @Mock
    private CalificacionRepository calificacionRepository;

    @InjectMocks
    private CalificacionServiceImpl calificacionService;

    @Test
    public void testGetAllCalificaciones() {
        // Arrange
        List<Calificacion> calificaciones = new ArrayList<>();
        calificaciones.add(new Calificacion(1, 5, 1, "Usuario1", "2024-04-29"));
        calificaciones.add(new Calificacion(2, 4, 1, "Usuario2", "2024-04-29"));
        when(calificacionRepository.findAll()).thenReturn(calificaciones);

        // Act
        List<Calificacion> resultado = calificacionService.getAllCalificaciones();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testSaveCalificacion() {
        // Arrange
        Calificacion calificacion = new Calificacion(1, 5, 1, "Usuario1", "2024-04-29");
        when(calificacionRepository.save(calificacion)).thenReturn(calificacion);

        // Act
        Calificacion resultado = calificacionService.saveCalificacion(calificacion);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(5, resultado.getCalificacion());
        assertEquals(1, resultado.getIdPublicacion());
        assertEquals("Usuario1", resultado.getRealizadaPor());
        assertEquals("2024-04-29", resultado.getFecha());
    }

    @Test
    public void testGetCalificacionById() {
        // Arrange
        Calificacion calificacion = new Calificacion(1, 5, 1, "Usuario1", "2024-04-29");
        Optional<Calificacion> optionalCalificacion = Optional.of(calificacion);
        when(calificacionRepository.findById(1)).thenReturn(optionalCalificacion);

        // Act
        Optional<Calificacion> resultado = calificacionService.getCalificacionById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.get().getId());
        assertEquals(5, resultado.get().getCalificacion());
        assertEquals(1, resultado.get().getIdPublicacion());
        assertEquals("Usuario1", resultado.get().getRealizadaPor());
        assertEquals("2024-04-29", resultado.get().getFecha());
    }

    @Test
    public void testDeleteCalificacion() {
        // Arrange
        int id = 1;
        when(calificacionRepository.existsById(id)).thenReturn(true);

        // Act
        boolean resultado = calificacionService.deleteCalificacion(id);

        // Assert
        assertEquals(true, resultado);
    }

    @Test
    public void testGetPromedioCalificacionesByPublicacionId() {
        // Arrange
        int idPublicacion = 1;
        double promedio = 4.5;
        when(calificacionRepository.findPromedioCalificacionesByPublicacionId(idPublicacion)).thenReturn(promedio);

        // Act
        double resultado = calificacionService.getPromedioCalificacionesByPublicacionId(idPublicacion);

        // Assert
        assertEquals(promedio, resultado);
    }
}
