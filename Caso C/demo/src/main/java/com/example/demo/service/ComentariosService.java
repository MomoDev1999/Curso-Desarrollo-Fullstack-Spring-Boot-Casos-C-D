package com.example.demo.service;

import com.example.demo.model.Comentarios;

import java.util.List;
import java.util.Optional;

public interface ComentariosService {
    List<Comentarios> getAllComentarios();

    Comentarios saveComentarios(Comentarios comentarios);

    Optional<Comentarios> getComentarioById(int id);

    boolean deleteComentario(int id);

    List<Comentarios> getComentariosByPublicacionId(int idPublicacion); // Nuevo método
}
