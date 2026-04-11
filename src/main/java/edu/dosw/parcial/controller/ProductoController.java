package edu.dosw.parcial.controller;

// FIX (enunciado): endpoint de consulta por código QR completamente faltante
import edu.dosw.parcial.controller.dtos.response.ProductoResponse;
import edu.dosw.parcial.core.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Productos", description = "Consulta de productos")
public class ProductoController {

    private final ProductoService productoService;


    @GetMapping("/{codigoQr}")
    @Operation(summary = "Consultar producto por código QR")
    public ResponseEntity<ProductoResponse> buscarPorCodigoQr(
            @PathVariable String codigoQr) {
        log.info("GET /api/productos/{} - consulta por QR", codigoQr);
        return ResponseEntity.ok(productoService.buscarPorCodigoQr(codigoQr));
    }
}