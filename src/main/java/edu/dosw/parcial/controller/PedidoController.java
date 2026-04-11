package edu.dosw.parcial.controller;

import edu.dosw.parcial.controller.dtos.request.CrearPedidoRequest;
import edu.dosw.parcial.controller.dtos.response.PedidoResponse;
import edu.dosw.parcial.core.models.enums.EstadoPedido;
import edu.dosw.parcial.core.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Pedidos", description = "Gestión de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PedidoResponse> crearPedido(
            @Valid @RequestBody CrearPedidoRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST /api/pedidos - usuario: {}", userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.crearPedido(userDetails.getUsername(), request));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado del pedido - solo ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PedidoResponse> cambiarEstado(
            @PathVariable String id,
            @RequestParam EstadoPedido nuevoEstado,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("PATCH /api/pedidos/{}/estado - admin: {}", id, userDetails.getUsername());
        return ResponseEntity.ok(
                pedidoService.cambiarEstado(id, nuevoEstado, userDetails.getUsername()));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido - solo CLIENTE en estado CREADO")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PedidoResponse> cancelarPedido(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("PATCH /api/pedidos/{}/cancelar - cliente: {}", id, userDetails.getUsername());
        return ResponseEntity.ok(
                pedidoService.cancelarPedido(id, userDetails.getUsername()));
    }
}