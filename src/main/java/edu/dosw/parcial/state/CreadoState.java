package edu.dosw.parcial.state;

import edu.dosw.parcial.core.models.Pedido;
import edu.dosw.parcial.core.models.enums.EstadoPedido;
import org.springframework.stereotype.Component;

@Component
public class CreadoState implements OrderState {

    @Override
    public void iniciarPreparacion(Pedido pedido) {
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("No se puede entregar un pedido que no está en preparación");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setEstado(EstadoPedido.CANCELADO);
    }
}
