// PublicacionServiceImpl.java
package com.example.demo.service;

import com.example.demo.model.Publicacion;
import com.example.demo.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServiceImpl implements PublicacionService {
    @Autowired
    private final PublicacionRepository publicacionRepository;

    public PublicacionServiceImpl(PublicacionRepository publicacionRepository) {
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }

    @Override
    public Publicacion savePublicacion(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }

    @Override
    public Optional<Publicacion> getPublicacionById(int id) {
        return publicacionRepository.findById(id);
    }

    @Override
    public boolean deletePublicacion(int id) {
        Optional<Publicacion> publicacionOptional = publicacionRepository.findById(id);
        if (publicacionOptional.isPresent()) {
            publicacionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}