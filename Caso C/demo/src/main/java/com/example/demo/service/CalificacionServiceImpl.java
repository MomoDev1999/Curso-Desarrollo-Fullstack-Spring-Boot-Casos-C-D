package com.example.demo.service;

import com.example.demo.model.Calificacion;
import com.example.demo.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    private final CalificacionRepository calificacionRepository;

    public CalificacionServiceImpl(CalificacionRepository calificacionRepository) {
        this.calificacionRepository = calificacionRepository;
    }

    @Override
    public List<Calificacion> getAllCalificaciones() {
        return calificacionRepository.findAll();
    }

    @Override
    public Calificacion saveCalificacion(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    @Override
    public Optional<Calificacion> getCalificacionById(int id) {
        return calificacionRepository.findById(id);
    }

    @Override
    public boolean deleteCalificacion(int id) {
        if (calificacionRepository.existsById(id)) {
            calificacionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getPromedioCalificacionesByPublicacionId(int idPublicacion) {
        List<Calificacion> calificaciones = calificacionRepository.findAll(); // Obtener todas las calificaciones
        List<Calificacion> calificacionesDePublicacion = new ArrayList<>();

        // Filtrar las calificaciones por el ID de la publicación
        for (Calificacion calificacion : calificaciones) {
            if (calificacion.getIdPublicacion() == idPublicacion) {
                calificacionesDePublicacion.add(calificacion);
            }
        }

        if (calificacionesDePublicacion.isEmpty()) {
            return 0.0; // No hay calificaciones para esta publicación
        } else {
            int totalCalificaciones = 0;
            for (Calificacion calificacion : calificacionesDePublicacion) {
                totalCalificaciones += calificacion.getCalificacion();
            }
            return (double) totalCalificaciones / calificacionesDePublicacion.size();
        }
    }

}
