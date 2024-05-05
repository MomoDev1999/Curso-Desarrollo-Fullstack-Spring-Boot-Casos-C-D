package com.example.demo.controller;

import com.example.demo.model.Comentarios;
import com.example.demo.service.ComentariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        public CollectionModel<EntityModel<Comentarios>> getAllComentarios() {
                List<EntityModel<Comentarios>> comentarios = comentariosService.getAllComentarios().stream()
                                .map(comentario -> EntityModel.of(comentario,
                                                WebMvcLinkBuilder.linkTo(ComentarioController.class)
                                                                .slash(comentario.getId()).withSelfRel(),
                                                WebMvcLinkBuilder.linkTo(ComentarioController.class)
                                                                .slash("publicacion")
                                                                .slash(comentario.getIdPublicacion())
                                                                .withRel("publicacion")))
                                .collect(Collectors.toList());

                Link link = WebMvcLinkBuilder.linkTo(ComentarioController.class).withSelfRel();
                CollectionModel<EntityModel<Comentarios>> resources = CollectionModel.of(comentarios, link);
                return resources;
        }

        @GetMapping("/{id}")
        public ResponseEntity<EntityModel<Comentarios>> getComentarioById(@PathVariable int id) {
                Optional<Comentarios> comentarioOptional = comentariosService.getComentarioById(id);
                if (comentarioOptional.isPresent()) {
                        Comentarios comentario = comentarioOptional.get();
                        EntityModel<Comentarios> resource = EntityModel.of(comentario,
                                        WebMvcLinkBuilder.linkTo(ComentarioController.class).slash(comentario.getId())
                                                        .withSelfRel(),
                                        WebMvcLinkBuilder.linkTo(ComentarioController.class).slash("publicacion")
                                                        .slash(comentario.getIdPublicacion()).withRel("publicacion"));
                        return ResponseEntity.ok(resource);
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @PostMapping
        public ResponseEntity<EntityModel<Comentarios>> createComentario(@RequestBody Comentarios comentario) {
                Comentarios nuevoComentario = comentariosService.saveComentarios(comentario);
                EntityModel<Comentarios> resource = EntityModel.of(nuevoComentario,
                                WebMvcLinkBuilder.linkTo(ComentarioController.class).slash(nuevoComentario.getId())
                                                .withSelfRel(),
                                WebMvcLinkBuilder.linkTo(ComentarioController.class).slash("publicacion")
                                                .slash(nuevoComentario.getIdPublicacion()).withRel("publicacion"));
                return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        }

        @PutMapping("/{id}")
        public ResponseEntity<EntityModel<Comentarios>> updateComentario(@PathVariable int id,
                        @RequestBody Comentarios updatedComentario) {
                Optional<Comentarios> existingComentario = comentariosService.getComentarioById(id);
                if (existingComentario.isPresent()) {
                        Comentarios comentario = existingComentario.get();
                        comentario.setComentario(updatedComentario.getComentario());
                        comentario.setIdPublicacion(updatedComentario.getIdPublicacion());
                        comentario.setRealizadoPor(updatedComentario.getRealizadoPor());
                        comentario.setFecha(updatedComentario.getFecha());

                        Comentarios updated = comentariosService.saveComentarios(comentario);
                        EntityModel<Comentarios> resource = EntityModel.of(updated,
                                        WebMvcLinkBuilder.linkTo(ComentarioController.class).slash(updated.getId())
                                                        .withSelfRel(),
                                        WebMvcLinkBuilder.linkTo(ComentarioController.class).slash("publicacion")
                                                        .slash(updated.getIdPublicacion()).withRel("publicacion"));
                        return ResponseEntity.ok(resource);
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
                List<EntityModel<Comentarios>> comentarios = comentariosService
                                .getComentariosByPublicacionId(idPublicacion)
                                .stream()
                                .map(comentario -> EntityModel.of(comentario,
                                                WebMvcLinkBuilder.linkTo(ComentarioController.class)
                                                                .slash(comentario.getId()).withSelfRel()))
                                .collect(Collectors.toList());

                Link link = WebMvcLinkBuilder.linkTo(ComentarioController.class).slash("publicacion")
                                .slash(idPublicacion).withSelfRel();
                CollectionModel<EntityModel<Comentarios>> resources = CollectionModel.of(comentarios, link);
                return ResponseEntity.ok(resources);
        }
}
