package edu.dosw.parcial.persistence.repositories;

import edu.dosw.parcial.core.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    Optional<Producto> findByCodigoQr(String codigoQr);
}
