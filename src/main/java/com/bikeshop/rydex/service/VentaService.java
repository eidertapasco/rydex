package com.bikeshop.rydex.service;

import com.bikeshop.rydex.dto.request.VentaRequest;
import com.bikeshop.rydex.enums.RolUsuario;
import com.bikeshop.rydex.model.*;
import com.bikeshop.rydex.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final BicicletaRepository bicicletaRepository;
    private final PasswordEncoder passwordEncoder; // NUEVO: Para crear la contraseña del cliente nuevo

    @Transactional
    public VentaModel createVenta(VentaRequest request) {
        ClienteModel cliente;

        // --- AQUI EMPIEZA LO NUEVO DE LA LOGICA DE CLIENTES ---
        // 1. Validamos si la petición trae un ID de cliente (Venta Web o Admin eligió uno existente)
        if (request.getIdCliente() != null) {
            cliente = clienteRepository.findById(request.getIdCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        } else {
            // 2. Si no trae ID, es una Venta Física a un cliente nuevo. Validamos que envíe la cédula
            if (request.getNuevoClienteDocumento() == null || request.getNuevoClienteDocumento().isBlank()) {
                throw new RuntimeException("Debe enviar el idCliente o los datos del nuevo cliente");
            }

            // 3. Buscamos si la cédula ya existe. Si no, creamos el cliente en tiempo real.
            // (Asegúrate de tener Optional<ClienteModel> findByDocumento(String documento) en tu ClienteRepository)
            cliente = clienteRepository.findByDocumento(request.getNuevoClienteDocumento())
                    .orElseGet(() -> {
                        ClienteModel nuevoCliente = new ClienteModel();
                        nuevoCliente.setNombre(request.getNuevoClienteNombre());
                        nuevoCliente.setDocumento(request.getNuevoClienteDocumento());
                        nuevoCliente.setTelefono(request.getNuevoClienteTelefono());
                        // Si no manda email, le creamos uno genérico con su cédula
                        nuevoCliente.setEmail(request.getNuevoClienteEmail() != null && !request.getNuevoClienteEmail().isBlank()
                                ? request.getNuevoClienteEmail()
                                : request.getNuevoClienteDocumento() + "@rydex.com");

                        // Contraseña por defecto = su cédula
                        nuevoCliente.setPassword(passwordEncoder.encode(request.getNuevoClienteDocumento()));
                        nuevoCliente.setRol(RolUsuario.CLIENTE);

                        return clienteRepository.save(nuevoCliente);
                    });
        }

        VentaModel venta = new VentaModel();
        venta.setCliente(cliente);
        venta.setTotal(request.getTotal());
        venta.setFecha(java.time.LocalDateTime.now());

        // --- AQUI AGREGAMOS LA DIRECCION DE ENVIO ---
        // Si viene dirección, la guardamos. Si no, asumimos que fue en tienda física.
        venta.setDireccionEnvio(request.getDireccionEnvio() != null && !request.getDireccionEnvio().isBlank()
                ? request.getDireccionEnvio()
                : "Entrega en Tienda Física");

        List<DetalleVentaModel> detalles = new ArrayList<>();
        for (VentaRequest.DetalleVentaRequest d : request.getDetalles()) {
            BicicletaModel bici = bicicletaRepository.findById(d.getIdBicicleta())
                    .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada: " + d.getIdBicicleta()));

            if (bici.getStockActual() < d.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + bici.getMarca() + " " + bici.getModelo());
            }
            bici.setStockActual(bici.getStockActual() - d.getCantidad());
            bicicletaRepository.save(bici);

            DetalleVentaModel detalle = new DetalleVentaModel();
            detalle.setVenta(venta);
            detalle.setBicicleta(bici);
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setSubtotal(d.getSubtotal());
            detalles.add(detalle);
        }

        venta.setDetalles(detalles);
        return ventaRepository.save(venta);
    }

    public List<VentaModel> findAll() {
        return ventaRepository.findAll();
    }

    public List<VentaModel> findByFecha(String fecha) {
        java.time.LocalDate date = java.time.LocalDate.parse(fecha);
        java.time.LocalDateTime start = date.atStartOfDay();
        java.time.LocalDateTime end   = date.atTime(23, 59, 59);
        return ventaRepository.findByFechaBetween(start, end);
    }

    public List<VentaModel> findByRango(String fechaInicio, String fechaFin) {
        java.time.LocalDate start = java.time.LocalDate.parse(fechaInicio);
        java.time.LocalDate end = java.time.LocalDate.parse(fechaFin);

        // Buscamos desde las 00:00:00 del primer día hasta las 23:59:59 del último día
        return ventaRepository.findByFechaBetween(start.atStartOfDay(), end.atTime(23, 59, 59));
    }

    public Map<String, Object> ventaToMap(VentaModel v) {
        return Map.of(
                "id_venta", v.getIdVenta(),
                "fecha",    v.getFecha(),
                "total",    v.getTotal()
        );
    }

    // Metodo para buscar una venta por su ID
    public VentaModel findById(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    // Devuelve solo las compras del usuario autenticado
    public List<VentaModel> getMisCompras(String emailCliente) {
        return ventaRepository.findByCliente_Email(emailCliente);
    }
}