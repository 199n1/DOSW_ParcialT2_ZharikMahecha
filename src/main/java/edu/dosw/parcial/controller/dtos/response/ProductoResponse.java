package edu.dosw.parcial.controller.dtos.response;

import edu.dosw.parcial.core.models.enums.EstadoProducto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoResponse {
    private String id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String codigoQr;
    private Integer stock;
    private EstadoProducto estado;
}