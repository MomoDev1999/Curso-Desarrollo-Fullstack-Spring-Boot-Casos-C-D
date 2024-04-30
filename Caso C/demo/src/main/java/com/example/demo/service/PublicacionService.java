// PublicacionService.java
package com.example.demo.service;

import com.example.demo.model.Publicacion;
import java.util.List;
import java.util.Optional;

public interface PublicacionService {
    List<Publicacion> getAllPublicaciones();

    Publicacion savePublicacion(Publicacion publicacion);

    Optional<Publicacion> getPublicacionById(int id);

    boolean deletePublicacion(int id);

}