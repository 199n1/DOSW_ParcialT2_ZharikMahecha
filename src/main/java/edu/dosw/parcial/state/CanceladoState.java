package edu.dosw.parcial.state;

import edu.dosw.parcial.core.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class CanceladoState implements OrderState {

    @Override
    public void iniciarPreparacion(Pedido pedido) {
        throw new IllegalStateException("El pedido está cancelado");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("El pedido está cancelado");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya está cancelado");
    }
}