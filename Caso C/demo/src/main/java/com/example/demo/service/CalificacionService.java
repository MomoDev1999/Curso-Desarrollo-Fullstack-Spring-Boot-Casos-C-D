package com.example.demo.service;

import com.example.demo.model.Calificacion;
import java.util.List;
import java.util.Optional;

public interface CalificacionService {
    List<Calificacion> getAllCalificaciones();

    Calificacion saveCalificacion(Calificacion calificacion);

    Optional<Calificacion> getCalificacionById(int id);

    boolean deleteCalificacion(int id);

    double getPromedioCalificacionesByPublicacionId(int idPublicacion);
}