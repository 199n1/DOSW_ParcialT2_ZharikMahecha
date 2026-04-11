package edu.dosw.parcial.state;

import edu.dosw.parcial.core.models.enums.EstadoPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStateFactory {

    private final CreadoState creadoState;
    private final EnPreparacionState enPreparacionState;
    private final EntregadoState entregadoState;
    private final CanceladoState canceladoState;

    public OrderState getState(EstadoPedido estado) {
        return switch (estado) {
            case CREADO -> creadoState;
            case EN_PREPARACION -> enPreparacionState;
            case ENTREGADO -> entregadoState;
            case CANCELADO -> canceladoState;
        };
    }
}
