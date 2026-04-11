package edu.dosw.parcial.core.services;

import edu.dosw.parcial.controller.dtos.response.ProductoResponse;
import edu.dosw.parcial.controller.mappers.ProductoMapper;
import edu.dosw.parcial.core.models.Producto;
import edu.dosw.parcial.persistence.repositories.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoResponse buscarPorCodigoQr(String codigoQr) {
        log.info("Buscando producto con codigoQr: {}", codigoQr);

        Producto producto = productoRepository.findByCodigoQr(codigoQr)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Producto no encontrado con código QR: " + codigoQr));

        log.info("Producto encontrado: {}", producto.getNombre());
        return productoMapper.toResponse(producto);
    }
}