package com.example.demo.controller;

import com.example.demo.model.Publicacion;
import com.example.demo.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<List<EntityModel<Publicacion>>> getAllPublicaciones() {
        List<EntityModel<Publicacion>> publicaciones = publicacionService.getAllPublicaciones().stream()
                .map(publicacion -> EntityModel.of(publicacion,
                        Link.of("/publicaciones/" + publicacion.getId()).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(publicaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Publicacion>> getPublicacionById(@PathVariable int id) {
        Optional<Publicacion> publicacion = publicacionService.getPublicacionById(id);
        return publicacion.map(pub -> EntityModel.of(pub,
                Link.of("/publicaciones/" + id).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Publicacion>> createPublicacion(@RequestBody Publicacion publicacion) {
        Publicacion nuevaPublicacion = publicacionService.savePublicacion(publicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(nuevaPublicacion,
                Link.of("/publicaciones/" + nuevaPublicacion.getId()).withSelfRel()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Publicacion>> updatePublicacion(@PathVariable int id,
            @RequestBody Publicacion updatedPublicacion) {
        Optional<Publicacion> existingPublicacion = publicacionService.getPublicacionById(id);

        if (existingPublicacion.isPresent()) {
            Publicacion publicacion = existingPublicacion.get();
            // Actualizar los campos de la publicación con los datos recibidos
            publicacion.setTitulo(updatedPublicacion.getTitulo());
            publicacion.setAutor(updatedPublicacion.getAutor());
            publicacion.setFecha(updatedPublicacion.getFecha());
            publicacion.setDescripcion(updatedPublicacion.getDescripcion());

            // Guardar la publicación actualizada en la base de datos
            Publicacion updated = publicacionService.savePublicacion(publicacion);
            return ResponseEntity.ok(EntityModel.of(updated,
                    Link.of("/publicaciones/" + updated.getId()).withSelfRel()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable int id) {
        boolean deleted = publicacionService.deletePublicacion(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
