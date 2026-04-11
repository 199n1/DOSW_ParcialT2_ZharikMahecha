package edu.dosw.parcial.state;

import edu.dosw.parcial.core.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class EntregadoState implements OrderState {

    @Override
    public void iniciarPreparacion(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue entregado");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("El pedido ya fue entregado");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("No se puede cancelar un pedido entregado");
    }
}