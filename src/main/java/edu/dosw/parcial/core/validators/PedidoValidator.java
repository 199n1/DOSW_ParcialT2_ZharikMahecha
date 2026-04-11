package edu.dosw.parcial.core.validators;

import edu.dosw.parcial.controller.dtos.request.ItemPedidoRequest;
import edu.dosw.parcial.core.exceptions.PedidoActivoException;
import edu.dosw.parcial.core.exceptions.StockInsuficienteException;
import edu.dosw.parcial.core.models.Producto;
import edu.dosw.parcial.core.models.Usuario;
import edu.dosw.parcial.core.models.enums.EstadoPedido;
import edu.dosw.parcial.core.models.enums.EstadoProducto;
import edu.dosw.parcial.persistence.repositories.PedidoRepository;
import edu.dosw.parcial.persistence.repositories.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public void validarCreacion(Usuario usuario, List<ItemPedidoRequest> items) {
        if (pedidoRepository.existsByUsuarioAndEstado(usuario, EstadoPedido.CREADO)) {
            throw new PedidoActivoException("El usuario ya tiene un pedido activo");
        }

        for (ItemPedidoRequest item : items) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Producto no encontrado: " + item.getProductoId()));

            if (producto.getEstado() == EstadoProducto.NO_DISPONIBLE) {
                throw new StockInsuficienteException(
                        "El producto " + producto.getNombre() + " no está disponible");
            }

            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para: " + producto.getNombre());
            }
        }
    }
}
