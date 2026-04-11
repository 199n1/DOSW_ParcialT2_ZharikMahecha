package edu.dosw.parcial.state;

import edu.dosw.parcial.core.models.Pedido;

public interface OrderState {
    void iniciarPreparacion(Pedido pedido);
    void entregar(Pedido pedido);
    void cancelar(Pedido pedido);
}
