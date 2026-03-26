package com.bikeshop.rydex.service;

import com.bikeshop.rydex.dto.request.CompraRequest;
import com.bikeshop.rydex.model.*;
import com.bikeshop.rydex.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;
    private final BicicletaRepository bicicletaRepository;

    @Transactional
    public CompraModel createCompra(CompraRequest request) {
        ProveedorModel proveedor = proveedorRepository.findById(request.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        CompraModel compra = new CompraModel();
        compra.setProveedor(proveedor);
        compra.setTotal(request.getTotal());

        List<DetalleCompraModel> detalles = new ArrayList<>();
        for (CompraRequest.DetalleCompraRequest d : request.getDetalles()) {
            BicicletaModel bici = bicicletaRepository.findById(d.getIdBicicleta())
                    .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada: " + d.getIdBicicleta()));

            // Al comprar, aumentar el stock
            bici.setStockActual(bici.getStockActual() + d.getCantidad());
            bicicletaRepository.save(bici);

            DetalleCompraModel detalle = new DetalleCompraModel();
            detalle.setCompra(compra);
            detalle.setBicicleta(bici);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setSubtotal(d.getSubtotal());
            detalles.add(detalle);
        }

        compra.setDetalles(detalles);
        return compraRepository.save(compra);
    }

    public List<CompraModel> findAll() {
        return compraRepository.findAll();
    }
}