package edu.dosw.parcial.core.services;

import edu.dosw.parcial.controller.dtos.request.CrearPedidoRequest;
import edu.dosw.parcial.controller.dtos.request.ItemPedidoRequest;
import edu.dosw.parcial.controller.dtos.response.PedidoResponse;
import edu.dosw.parcial.controller.mappers.PedidoMapper;
import edu.dosw.parcial.core.models.*;
import edu.dosw.parcial.core.models.enums.EstadoPedido;
import edu.dosw.parcial.core.models.enums.EstadoProducto;
import edu.dosw.parcial.core.models.enums.Rol;
import edu.dosw.parcial.core.validators.PedidoValidator;
import edu.dosw.parcial.persistence.repositories.PedidoRepository;
import edu.dosw.parcial.persistence.repositories.ProductoRepository;
import edu.dosw.parcial.persistence.repositories.UsuarioRepository;
import edu.dosw.parcial.state.OrderStateFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock private PedidoRepository pedidoRepository;
    @Mock private ProductoRepository productoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private PedidoValidator pedidoValidator;
    @Mock private OrderStateFactory orderStateFactory;
    @Mock private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoService pedidoService;

    private Usuario usuario;
    private Producto producto;
    private CrearPedidoRequest request;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id("u1")
                .correo("juan@uni.edu.co")
                .rol(Rol.CLIENTE)
                .build();

        producto = Producto.builder()
                .id("p1")
                .nombre("Café")
                .precio(2500.0)
                .stock(10)
                .estado(EstadoProducto.DISPONIBLE)
                .build();

        ItemPedidoRequest item = new ItemPedidoRequest();
        item.setProductoId("p1");
        item.setCantidad(2);

        request = new CrearPedidoRequest();
        request.setItems(List.of(item));
    }

    @Test
    void deberiaCrearPedidoExitosamente() {
        when(usuarioRepository.findByCorreo("juan@uni.edu.co"))
                .thenReturn(Optional.of(usuario));
        when(productoRepository.findById("p1"))
                .thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));
        when(pedidoMapper.toResponse(any()))
                .thenReturn(PedidoResponse.builder()
                        .estado(EstadoPedido.CREADO)
                        .total(5000.0)
                        .build());

        PedidoResponse response = pedidoService.crearPedido("juan@uni.edu.co", request);

        assertNotNull(response);
        assertEquals(EstadoPedido.CREADO, response.getEstado());
        assertEquals(5000.0, response.getTotal());
        verify(pedidoValidator).validarCreacion(usuario, request.getItems());
    }

    @Test
    void deberieFallarSiUsuarioNoExiste() {
        when(usuarioRepository.findByCorreo(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> pedidoService.crearPedido("noexiste@uni.edu.co", request));

        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void deberieCambiarEstadoAEnPreparacion() {
        Pedido pedido = Pedido.builder()
                .id("ped1")
                .estado(EstadoPedido.CREADO)
                .usuario(usuario)
                .items(List.of())
                .total(0.0)
                .build();

        edu.dosw.parcial.state.CreadoState creadoState =
                new edu.dosw.parcial.state.CreadoState();

        when(pedidoRepository.findById("ped1")).thenReturn(Optional.of(pedido));
        when(orderStateFactory.getState(EstadoPedido.CREADO)).thenReturn(creadoState);
        when(pedidoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(pedidoMapper.toResponse(any()))
                .thenReturn(PedidoResponse.builder()
                        .estado(EstadoPedido.EN_PREPARACION)
                        .build());

        PedidoResponse response = pedidoService.cambiarEstado(
                "ped1", EstadoPedido.EN_PREPARACION, "admin@uni.edu.co");

        assertEquals(EstadoPedido.EN_PREPARACION, response.getEstado());
    }

    @Test
    void deberieFallarSiPedidoNoExisteAlCambiarEstado() {
        when(pedidoRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> pedidoService.cambiarEstado(
                        "noexiste", EstadoPedido.EN_PREPARACION, "admin@uni.edu.co"));
    }

    @Test
    void deberiaCancelarPedidoExitosamente() {
        Pedido pedido = Pedido.builder()
                .id("ped1")
                .estado(EstadoPedido.CREADO)
                .usuario(usuario)
                .items(List.of())
                .total(0.0)
                .build();

        edu.dosw.parcial.state.CreadoState creadoState =
                new edu.dosw.parcial.state.CreadoState();

        when(pedidoRepository.findById("ped1")).thenReturn(Optional.of(pedido));
        when(orderStateFactory.getState(EstadoPedido.CREADO)).thenReturn(creadoState);
        when(pedidoRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(pedidoMapper.toResponse(any()))
                .thenReturn(PedidoResponse.builder()
                        .estado(EstadoPedido.CANCELADO)
                        .build());

        PedidoResponse response = pedidoService.cancelarPedido("ped1", "juan@uni.edu.co");

        assertEquals(EstadoPedido.CANCELADO, response.getEstado());
    }

    @Test
    void deberieFallarAlCancelarSiNoEsElDueno() {
        Pedido pedido = Pedido.builder()
                .id("ped1")
                .estado(EstadoPedido.CREADO)
                .usuario(usuario)
                .items(List.of())
                .total(0.0)
                .build();

        when(pedidoRepository.findById("ped1")).thenReturn(Optional.of(pedido));

        assertThrows(IllegalArgumentException.class,
                () -> pedidoService.cancelarPedido("ped1", "otro@uni.edu.co"));
    }
}