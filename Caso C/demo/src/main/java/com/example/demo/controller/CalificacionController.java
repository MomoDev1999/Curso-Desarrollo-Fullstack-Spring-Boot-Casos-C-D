package com.example.demo.controller;

import com.example.demo.model.Calificacion;
import com.example.demo.service.CalificacionService;
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
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @GetMapping
    public ResponseEntity<List<EntityModel<Calificacion>>> getAllCalificaciones() {
        List<EntityModel<Calificacion>> calificaciones = calificacionService.getAllCalificaciones().stream()
                .map(calificacion -> EntityModel.of(calificacion,
                        Link.of("/calificaciones/" + calificacion.getId()).withSelfRel(),
                        Link.of("/calificaciones/promedio/" + calificacion.getIdPublicacion()).withRel("promedio")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(calificaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Calificacion>> getCalificacionById(@PathVariable int id) {
        Optional<Calificacion> calificacion = calificacionService.getCalificacionById(id);
        return calificacion.map(cal -> EntityModel.of(cal,
                Link.of("/calificaciones/" + id).withSelfRel(),
                Link.of("/calificaciones/promedio/" + cal.getIdPublicacion()).withRel("promedio")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Calificacion>> createCalificacion(@RequestBody Calificacion calificacion) {
        Calificacion nuevaCalificacion = calificacionService.saveCalificacion(calificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(nuevaCalificacion,
                Link.of("/calificaciones/" + nuevaCalificacion.getId()).withSelfRel(),
                Link.of("/calificaciones/promedio/" + nuevaCalificacion.getIdPublicacion()).withRel("promedio")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Calificacion>> updateCalificacion(@PathVariable int id,
            @RequestBody Calificacion updatedCalificacion) {
        Optional<Calificacion> existingCalificacion = calificacionService.getCalificacionById(id);

        if (existingCalificacion.isPresent()) {
            Calificacion calificacion = existingCalificacion.get();
            // Actualizar los campos de la calificación con los datos recibidos
            calificacion.setCalificacion(updatedCalificacion.getCalificacion());
            calificacion.setIdPublicacion(updatedCalificacion.getIdPublicacion());
            calificacion.setRealizadaPor(updatedCalificacion.getRealizadaPor());
            calificacion.setFecha(updatedCalificacion.getFecha());

            // Guardar la calificación actualizada en la base de datos
            Calificacion updated = calificacionService.saveCalificacion(calificacion);
            return ResponseEntity.ok(EntityModel.of(updated,
                    Link.of("/calificaciones/" + updated.getId()).withSelfRel(),
                    Link.of("/calificaciones/promedio/" + updated.getIdPublicacion()).withRel("promedio")));
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
