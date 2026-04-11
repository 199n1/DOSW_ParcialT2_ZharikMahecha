package edu.dosw.parcial.state;

import edu.dosw.parcial.core.models.Pedido;
import edu.dosw.parcial.core.models.enums.EstadoPedido;
import org.springframework.stereotype.Component;

@Component
public class EnPreparacionState implements OrderState {

    @Override
    public void iniciarPreparacion(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está en preparación");
    }

    @Override
    public void entregar(Pedido pedido) {
        pedido.setEstado(EstadoPedido.ENTREGADO);
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("No se puede cancelar un pedido en preparación");
    }
}
