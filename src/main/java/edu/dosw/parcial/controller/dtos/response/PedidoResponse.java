package edu.dosw.parcial.controller.dtos.response;

import edu.dosw.parcial.core.models.enums.EstadoPedido;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoResponse {
    private String id;
    private String usuarioId;
    private List<ItemPedidoResponse> items;
    private EstadoPedido estado;
    private Double total;
    private LocalDateTime fechaCreacion;
}