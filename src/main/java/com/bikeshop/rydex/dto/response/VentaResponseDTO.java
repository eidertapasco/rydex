package com.bikeshop.rydex.dto.response;

import com.bikeshop.rydex.dto.ClienteDTO;
import com.bikeshop.rydex.dto.request.VentaRequestDTO;
import com.bikeshop.rydex.model.DetalleVentaModel;

import java.time.LocalDateTime;
import java.util.List;

public class VentaResponseDTO {

    private LocalDateTime fecha;

    private ClienteDTO nombre;

    private List<DetalleVentaModel> detalles;

    public VentaResponseDTO(LocalDateTime fecha, ClienteDTO nombre, List<VentaRequestDTO> lista){
        this.fecha = fecha;
        this.nombre = nombre;
        this.detalles = detalles;
    }

    public LocalDateTime getFecha(){
        return fecha;
    }
}
