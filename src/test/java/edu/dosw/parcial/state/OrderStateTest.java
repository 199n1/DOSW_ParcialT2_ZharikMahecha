package edu.dosw.parcial.state;

import edu.dosw.parcial.core.models.Pedido;
import edu.dosw.parcial.core.models.enums.EstadoPedido;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStateTest {

    @Test
    void creadoStateDeberiaPermitirIniciarPreparacion() {
        CreadoState state = new CreadoState();
        Pedido pedido = Pedido.builder().estado(EstadoPedido.CREADO).build();

        state.iniciarPreparacion(pedido);

        assertEquals(EstadoPedido.EN_PREPARACION, pedido.getEstado());
    }

    @Test
    void creadoStateDeberiaPermitirCancelar() {
        CreadoState state = new CreadoState();
        Pedido pedido = Pedido.builder().estado(EstadoPedido.CREADO).build();

        state.cancelar(pedido);

        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
    }

    @Test
    void creadoStateDeberiaFallarAlEntregar() {
        CreadoState state = new CreadoState();
        Pedido pedido = Pedido.builder().estado(EstadoPedido.CREADO).build();

        assertThrows(IllegalStateException.class,
                () -> state.entregar(pedido));
    }

    @Test
    void enPreparacionStateDeberiaPermitirEntregar() {
        EnPreparacionState state = new EnPreparacionState();
        Pedido pedido = Pedido.builder().estado(EstadoPedido.EN_PREPARACION).build();

        state.entregar(pedido);

        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstado());
    }

    @Test
    void enPreparacionStateDeberiaFallarAlCancelar() {
        EnPreparacionState state = new EnPreparacionState();
        Pedido pedido = Pedido.builder().estado(EstadoPedido.EN_PREPARACION).build();

        assertThrows(IllegalStateException.class,
                () -> state.cancelar(pedido));
    }

    @Test
    void entregadoStateDeberiaFallarEnCualquierTransicion() {
        EntregadoState state = new EntregadoState();
        Pedido pedido = Pedido.builder().estado(EstadoPedido.ENTREGADO).build();

        assertThrows(IllegalStateException.class,
                () -> state.iniciarPreparacion(pedido));
        assertThrows(IllegalStateException.class,
                () -> state.entregar(pedido));
        assertThrows(IllegalStateException.class,
                () -> state.cancelar(pedido));
    }

    @Test
    void canceladoStateDeberiaFallarEnCualquierTransicion() {
        CanceladoState state = new CanceladoState();
        Pedido pedido = Pedido.builder().estado(EstadoPedido.CANCELADO).build();

        assertThrows(IllegalStateException.class,
                () -> state.iniciarPreparacion(pedido));
        assertThrows(IllegalStateException.class,
                () -> state.entregar(pedido));
        assertThrows(IllegalStateException.class,
                () -> state.cancelar(pedido));
    }
}