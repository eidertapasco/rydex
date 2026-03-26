package com.bikeshop.rydex.service;

import com.bikeshop.rydex.dto.request.BicicletaRequest;
import com.bikeshop.rydex.dto.response.BicicletaResponse;
import com.bikeshop.rydex.dto.response.PagedResponse;
import com.bikeshop.rydex.enums.TipoBicicleta;
import com.bikeshop.rydex.model.BicicletaModel;
import com.bikeshop.rydex.repository.BicicletaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BicicletaService {

    private final BicicletaRepository bicicletaRepository;

    public PagedResponse<BicicletaResponse> findAll(
            String tipo, String marca, BigDecimal precioMin,
            BigDecimal precioMax, String q, int page, int pageSize) {

        TipoBicicleta tipoBicicleta = null;
        if (tipo != null && !tipo.isBlank()) {
            try { tipoBicicleta = TipoBicicleta.valueOf(tipo); }
            catch (IllegalArgumentException ignored) {}
        }

        List<BicicletaModel> results = bicicletaRepository.findWithFilters(
                tipoBicicleta,
                (marca != null && !marca.isBlank()) ? marca : null,
                precioMin,
                precioMax,
                (q != null && !q.isBlank()) ? q : null
        );

        int total     = results.size();
        int fromIndex = Math.min(page * pageSize, total);
        int toIndex   = Math.min(fromIndex + pageSize, total);
        List<BicicletaResponse> pageData = results.subList(fromIndex, toIndex)
                .stream().map(BicicletaResponse::from).toList();

        return new PagedResponse<>(pageData, total, page, pageSize);
    }

    public BicicletaResponse findById(Long id) {
        BicicletaModel b = bicicletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada"));
        return BicicletaResponse.from(b);
    }

    public List<String> findMarcas() {
        return bicicletaRepository.findDistinctMarcas();
    }

    public BicicletaResponse create(BicicletaRequest request) {
        BicicletaModel b = mapRequestToModel(new BicicletaModel(), request);
        return BicicletaResponse.from(bicicletaRepository.save(b));
    }

    public BicicletaResponse update(Long id, BicicletaRequest request) {
        BicicletaModel b = bicicletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada"));
        mapRequestToModel(b, request);
        return BicicletaResponse.from(bicicletaRepository.save(b));
    }

    public void delete(Long id) {
        if (!bicicletaRepository.existsById(id)) {
            throw new RuntimeException("Bicicleta no encontrada");
        }
        bicicletaRepository.deleteById(id);
    }

    private BicicletaModel mapRequestToModel(BicicletaModel b, BicicletaRequest r) {
        b.setSku(r.getSku());
        b.setMarca(r.getMarca());
        b.setModelo(r.getModelo());
        b.setTipo(TipoBicicleta.valueOf(r.getTipo()));
        b.setPrecio(r.getPrecio());
        b.setStockActual(r.getStockActual());     // ahora usa getStockActual()
        b.setStockMinimo(r.getStockMinimo());     // ahora usa getStockMinimo()
        b.setDescripcion(r.getDescripcion());
        b.setEtiqueta(r.getEtiqueta());
        return b;
    }
}