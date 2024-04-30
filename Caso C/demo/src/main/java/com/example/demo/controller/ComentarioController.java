package com.example.demo.controller;

import com.example.demo.model.Comentarios;
import com.example.demo.service.ComentariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentariosService comentariosService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Comentarios>>> getAllComentarios() {
        List<EntityModel<Comentarios>> comentarios = comentariosService.getAllComentarios().stream()
                .map(comentario -> EntityModel.of(comentario,
                        Link.of("/comentarios/" + comentario.getId()).withSelfRel(),
                        Link.of("/comentarios/publicacion/" + comentario.getIdPublicacion()).withRel("publicacion")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(comentarios,
                Link.of("/comentarios").withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Comentarios>> getComentarioById(@PathVariable int id) {
        Optional<Comentarios> comentario = comentariosService.getComentarioById(id);
        return comentario.map(com -> EntityModel.of(com,
                Link.of("/comentarios/" + id).withSelfRel(),
                Link.of("/comentarios/publicacion/" + com.getIdPublicacion()).withRel("publicacion")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Comentarios>> createComentario(@RequestBody Comentarios comentario) {
        Comentarios nuevoComentario = comentariosService.saveComentarios(comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(nuevoComentario,
                Link.of("/comentarios/" + nuevoComentario.getId()).withSelfRel(),
                Link.of("/comentarios/publicacion/" + nuevoComentario.getIdPublicacion()).withRel("publicacion")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Comentarios>> updateComentario(@PathVariable int id,
            @RequestBody Comentarios updatedComentario) {
        Optional<Comentarios> existingComentario = comentariosService.getComentarioById(id);

        if (existingComentario.isPresent()) {
            Comentarios comentario = existingComentario.get();
            // Actualizar los campos del comentario con los datos recibidos
            comentario.setComentario(updatedComentario.getComentario());
            comentario.setIdPublicacion(updatedComentario.getIdPublicacion());
            comentario.setRealizadoPor(updatedComentario.getRealizadoPor());
            comentario.setFecha(updatedComentario.getFecha());

            // Guardar el comentario actualizado en la base de datos
            Comentarios updated = comentariosService.saveComentarios(comentario);
            return ResponseEntity.ok(EntityModel.of(updated,
                    Link.of("/comentarios/" + updated.getId()).withSelfRel(),
                    Link.of("/comentarios/publicacion/" + updated.getIdPublicacion()).withRel("publicacion")));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable int id) {
        boolean deleted = comentariosService.deleteComentario(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/publicacion/{idPublicacion}")
    public ResponseEntity<CollectionModel<EntityModel<Comentarios>>> getComentariosByPublicacionId(
            @PathVariable int idPublicacion) {
        List<EntityModel<Comentarios>> comentarios = comentariosService.getComentariosByPublicacionId(idPublicacion)
                .stream()
                .map(comentario -> EntityModel.of(comentario,
                        Link.of("/comentarios/" + comentario.getId()).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(comentarios,
                Link.of("/comentarios/publicacion/" + idPublicacion).withSelfRel()));
    }
}
