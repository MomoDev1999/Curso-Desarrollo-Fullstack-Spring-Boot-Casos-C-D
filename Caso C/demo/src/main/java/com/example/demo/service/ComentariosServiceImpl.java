package com.example.demo.service;

import com.example.demo.model.Comentarios;
import com.example.demo.repository.ComentariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentariosServiceImpl implements ComentariosService {
    @Autowired
    private final ComentariosRepository comentariosRepository;

    public ComentariosServiceImpl(ComentariosRepository comentariosRepository) {
        this.comentariosRepository = comentariosRepository;
    }

    @Override
    public List<Comentarios> getAllComentarios() {
        return comentariosRepository.findAll();
    }

    @Override
    public Comentarios saveComentarios(Comentarios comentarios) {
        return comentariosRepository.save(comentarios);
    }

    @Override
    public Optional<Comentarios> getComentarioById(int id) {
        return comentariosRepository.findById(id);
    }

    @Override
    public boolean deleteComentario(int id) {
        if (comentariosRepository.existsById(id)) {
            comentariosRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Comentarios> getComentariosByPublicacionId(int idPublicacion) {
        return comentariosRepository.findComentariosByPublicacionId(idPublicacion);
    }
}
