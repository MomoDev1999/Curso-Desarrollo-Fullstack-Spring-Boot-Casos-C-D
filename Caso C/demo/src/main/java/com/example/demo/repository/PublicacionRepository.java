package com.example.demo.repository;

import com.example.demo.model.Publicacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    // Aquí puedes agregar métodos adicionales según tus necesidades
}
