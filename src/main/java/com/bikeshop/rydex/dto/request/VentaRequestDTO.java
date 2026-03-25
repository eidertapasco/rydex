package com.bikeshop.rydex.dto.request;

import com.bikeshop.rydex.dto.ClienteDTO;
import com.bikeshop.rydex.model.DetalleVentaModel;
import com.bikeshop.rydex.model.VentaModel;

import java.util.List;

public class VentaRequestDTO {

    private ClienteDTO documento;

    private List<VentaModel> productos;
}
