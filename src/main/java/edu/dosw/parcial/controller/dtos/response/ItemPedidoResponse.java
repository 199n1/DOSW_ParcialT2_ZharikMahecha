package edu.dosw.parcial.controller.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemPedidoResponse {
    private String productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double subtotal;
}