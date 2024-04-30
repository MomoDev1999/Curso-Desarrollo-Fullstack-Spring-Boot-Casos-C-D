package com.example.demo.repository;

import com.example.demo.model.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComentariosRepository extends JpaRepository<Comentarios, Integer> {
    @Query("SELECT c FROM Comentarios c WHERE c.idPublicacion = :idPublicacion")
    List<Comentarios> findComentariosByPublicacionId(@Param("idPublicacion") int idPublicacion);
}
