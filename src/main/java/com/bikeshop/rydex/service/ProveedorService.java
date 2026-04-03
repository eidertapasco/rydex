package com.bikeshop.rydex.service;

import com.bikeshop.rydex.dto.request.ProveedorRequest;
import com.bikeshop.rydex.model.ProveedorModel;
import com.bikeshop.rydex.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<ProveedorModel> findAll() {
        return proveedorRepository.findAll();
    }

    public ProveedorModel create(ProveedorRequest request) {
        ProveedorModel p = mapToModel(new ProveedorModel(), request);
        return proveedorRepository.save(p);
    }

    public ProveedorModel update(Long id, ProveedorRequest request) {
        ProveedorModel p = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        mapToModel(p, request);
        return proveedorRepository.save(p);
    }

    public void delete(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
    }

    private ProveedorModel mapToModel(ProveedorModel p, ProveedorRequest r) {
        p.setNombreEmpresa(r.getNombreEmpresa());
        p.setPersonaContacto(r.getPersonaContacto());
        p.setTelefonoContacto(r.getTelefonoContacto());
        p.setEmailContacto(r.getEmailContacto());
        return p;
    }
}