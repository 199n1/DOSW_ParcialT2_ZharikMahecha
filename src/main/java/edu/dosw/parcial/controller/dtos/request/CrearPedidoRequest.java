package edu.dosw.parcial.controller.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CrearPedidoRequest {

    @NotNull(message = "La lista de productos no puede ser nula")
    @Size(min = 1, message = "Debe incluir al menos un producto")
    @Valid
    private List<ItemPedidoRequest> items;
}