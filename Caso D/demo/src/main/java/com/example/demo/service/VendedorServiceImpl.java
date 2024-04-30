package com.example.demo.service;

import com.example.demo.model.Vendedor;
import com.example.demo.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorServiceImpl implements VendedorService {

    @Autowired

    private VendedorRepository vendedorRepository;

    public VendedorServiceImpl(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    @Override
    public List<Vendedor> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    @Override
    public Vendedor saveVendedor(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    @Override
    public Optional<Vendedor> getVendedorById(int id) {
        return vendedorRepository.findById(id);
    }

    @Override
    public boolean deleteVendedor(int id) {
        if (vendedorRepository.existsById(id)) {
            vendedorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
