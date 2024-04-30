package com.example.demo.repository;

import com.example.demo.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
    @Query("SELECT AVG(c.calificacion) FROM Calificacion c WHERE c.idPublicacion = :idPublicacion")
    double findPromedioCalificacionesByPublicacionId(@Param("idPublicacion") int idPublicacion);
}
