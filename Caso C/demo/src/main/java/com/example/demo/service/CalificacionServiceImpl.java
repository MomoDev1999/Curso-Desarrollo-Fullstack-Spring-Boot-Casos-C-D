// CalificacionServiceImpl.java
package com.example.demo.service;

import com.example.demo.model.Calificacion;
import com.example.demo.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return calificacionRepository.findPromedioCalificacionesByPublicacionId(idPublicacion);
    }
}