package edu.dosw.parcial.persistence.repositories;

import edu.dosw.parcial.core.models.Pedido;
import edu.dosw.parcial.core.models.Usuario;
import edu.dosw.parcial.core.models.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
    boolean existsByUsuarioAndEstado(Usuario usuario, EstadoPedido estado);
    Optional<Pedido> findByUsuarioAndEstado(Usuario usuario, EstadoPedido estado);
}
