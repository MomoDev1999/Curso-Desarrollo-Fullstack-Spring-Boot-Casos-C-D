package com.example.demo.controller;

import com.example.demo.model.Calificacion;
import com.example.demo.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @GetMapping
    public CollectionModel<EntityModel<Calificacion>> getAllCalificaciones() {
        List<EntityModel<Calificacion>> calificaciones = calificacionService.getAllCalificaciones().stream()
                .map(calificacion -> EntityModel.of(calificacion,
                        WebMvcLinkBuilder.linkTo(CalificacionController.class).slash(calificacion.getId())
                                .withSelfRel(),
                        WebMvcLinkBuilder.linkTo(CalificacionController.class).slash("promedio")
                                .slash(calificacion.getIdPublicacion()).withRel("promedio")))
                .collect(Collectors.toList());

        Link link = WebMvcLinkBuilder.linkTo(CalificacionController.class).withSelfRel();
        CollectionModel<EntityModel<Calificacion>> resources = CollectionModel.of(calificaciones, link);
        return resources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Calificacion>> getCalificacionById(@PathVariable int id) {
        Optional<Calificacion> calificacionOptional = calificacionService.getCalificacionById(id);
        if (calificacionOptional.isPresent()) {
            Calificacion calificacion = calificacionOptional.get();
            EntityModel<Calificacion> resource = EntityModel.of(calificacion,
                    WebMvcLinkBuilder.linkTo(CalificacionController.class).slash(calificacion.getId()).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(CalificacionController.class).slash("promedio")
                            .slash(calificacion.getIdPublicacion()).withRel("promedio"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Calificacion>> createCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion nuevaCalificacion = calificacionService.saveCalificacion(calificacion);
        EntityModel<Calificacion> resource = EntityModel.of(nuevaCalificacion,
                WebMvcLinkBuilder.linkTo(CalificacionController.class).slash(nuevaCalificacion.getId()).withSelfRel(),
                WebMvcLinkBuilder.linkTo(CalificacionController.class).slash("promedio")
                        .slash(nuevaCalificacion.getIdPublicacion()).withRel("promedio"));
        return ResponseEntity.created(resource.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Calificacion>> updateCalificacion(@PathVariable int id,
            @RequestBody Calificacion updatedCalificacion) {
        Optional<Calificacion> existingCalificacion = calificacionService.getCalificacionById(id);
        if (existingCalificacion.isPresent()) {
            Calificacion calificacion = existingCalificacion.get();
            calificacion.setCalificacion(updatedCalificacion.getCalificacion());
            calificacion.setIdPublicacion(updatedCalificacion.getIdPublicacion());
            calificacion.setRealizadaPor(updatedCalificacion.getRealizadaPor());
            calificacion.setFecha(updatedCalificacion.getFecha());

            Calificacion updated = calificacionService.saveCalificacion(calificacion);
            EntityModel<Calificacion> resource = EntityModel.of(updated,
                    WebMvcLinkBuilder.linkTo(CalificacionController.class).slash(updated.getId()).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(CalificacionController.class).slash("promedio")
                            .slash(updated.getIdPublicacion()).withRel("promedio"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalificacion(@PathVariable int id) {
        boolean deleted = calificacionService.deleteCalificacion(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/promedio/{idPublicacion}")
    public ResponseEntity<Double> getPromedioCalificacionesByPublicacionId(@PathVariable int idPublicacion) {
        double promedio = calificacionService.getPromedioCalificacionesByPublicacionId(idPublicacion);
        return ResponseEntity.ok(promedio);
    }
}
