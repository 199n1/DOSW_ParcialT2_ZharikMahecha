package edu.dosw.parcial.core.services;

import edu.dosw.parcial.controller.dtos.request.CrearPedidoRequest;
import edu.dosw.parcial.controller.dtos.request.ItemPedidoRequest;
import edu.dosw.parcial.controller.dtos.response.PedidoResponse;
import edu.dosw.parcial.controller.mappers.PedidoMapper;
import edu.dosw.parcial.core.models.*;
import edu.dosw.parcial.persistence.repositories.PedidoRepository;
import edu.dosw.parcial.persistence.repositories.ProductoRepository;
import edu.dosw.parcial.persistence.repositories.UsuarioRepository;
import edu.dosw.parcial.core.validators.PedidoValidator;
import edu.dosw.parcial.state.OrderStateFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoValidator pedidoValidator;
    private final OrderStateFactory orderStateFactory;
    private final PedidoMapper pedidoMapper;

    public PedidoResponse crearPedido(String correoUsuario, CrearPedidoRequest request) {
        log.info("Creando pedido para usuario: {}", correoUsuario);

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        pedidoValidator.validarCreacion(usuario, request.getItems());

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .estado(EstadoPedido.CREADO)
                .fechaCreacion(LocalDateTime.now())
                .total(0.0)
                .items(new ArrayList<>())
                .build();

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        double total = 0;
        List<ItemPedido> items = new ArrayList<>();

        for (ItemPedidoRequest itemReq : request.getItems()) {
            Producto producto = productoRepository.findById(itemReq.getProductoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Producto no encontrado: " + itemReq.getProductoId()));

            ItemPedido item = ItemPedido.builder()
                    .pedido(pedidoGuardado)
                    .producto(producto)
                    .cantidad(itemReq.getCantidad())
                    .build();

            items.add(item);
            total += producto.getPrecio() * itemReq.getCantidad();

            producto.setStock(producto.getStock() - itemReq.getCantidad());
            productoRepository.save(producto);
        }

        pedidoGuardado.setItems(items);
        pedidoGuardado.setTotal(total);
        pedidoRepository.save(pedidoGuardado);

        log.info("Pedido creado exitosamente con id: {}", pedidoGuardado.getId());
        return pedidoMapper.toResponse(pedidoGuardado);
    }

    public PedidoResponse cambiarEstado(String pedidoId, EstadoPedido nuevoEstado,
                                        String correoAdmin) {
        log.info("Admin {} cambiando estado del pedido {} a {}",
                correoAdmin, pedidoId, nuevoEstado);

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Pedido no encontrado: " + pedidoId));

        switch (nuevoEstado) {
            case EN_PREPARACION -> orderStateFactory
                    .getState(pedido.getEstado())
                    .iniciarPreparacion(pedido);
            case ENTREGADO -> orderStateFactory
                    .getState(pedido.getEstado())
                    .entregar(pedido);
            default -> throw new IllegalArgumentException(
                    "El admin solo puede cambiar a EN_PREPARACION o ENTREGADO");
        }

        pedidoRepository.save(pedido);
        log.info("Estado del pedido {} actualizado a {}", pedidoId, nuevoEstado);
        return pedidoMapper.toResponse(pedido);
    }

    public PedidoResponse cancelarPedido(String pedidoId, String correoCliente) {
        log.info("Cliente {} cancelando pedido {}", correoCliente, pedidoId);

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Pedido no encontrado: " + pedidoId));

        if (!pedido.getUsuario().getCorreo().equals(correoCliente)) {
            throw new IllegalArgumentException(
                    "No tienes permiso para cancelar este pedido");
        }

        orderStateFactory.getState(pedido.getEstado()).cancelar(pedido);

        for (ItemPedido item : pedido.getItems()) {
            Producto producto = item.getProducto();
            producto.setStock(producto.getStock() + item.getCantidad());
            productoRepository.save(producto);
        }

        pedidoRepository.save(pedido);
        log.info("Pedido {} cancelado exitosamente", pedidoId);
        return pedidoMapper.toResponse(pedido);
    }
}