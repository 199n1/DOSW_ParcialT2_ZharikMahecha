package edu.dosw.parcial.persistence.repositories;

import edu.dosw.parcial.core.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
