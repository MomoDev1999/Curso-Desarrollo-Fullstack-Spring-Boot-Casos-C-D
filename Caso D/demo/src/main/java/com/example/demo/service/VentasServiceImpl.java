package com.example.demo.service;

import com.example.demo.model.Ventas;
import com.example.demo.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentasServiceImpl implements VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    public VentasServiceImpl(VentasRepository ventasRepository) {
        this.ventasRepository = ventasRepository;
    }

    @Override
    public List<Ventas> getAllVentas() {
        return ventasRepository.findAll();
    }

    @Override
    public Ventas saveVentas(Ventas ventas) {
        return ventasRepository.save(ventas);
    }

    @Override
    public Optional<Ventas> getVentasById(int id) {
        return ventasRepository.findById(id);
    }

    @Override
    public boolean deleteVentas(int id) {
        if (ventasRepository.existsById(id)) {
            ventasRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
