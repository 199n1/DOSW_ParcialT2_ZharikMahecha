package edu.dosw.parcial.core.services;

import edu.dosw.parcial.controller.dtos.request.RegisterRequest;
import edu.dosw.parcial.controller.dtos.response.AuthResponse;
import edu.dosw.parcial.core.models.Usuario;
import edu.dosw.parcial.core.models.enums.Rol;
import edu.dosw.parcial.persistence.repositories.UsuarioRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        log.info("Registrando usuario con correo: {}", request.getCorreo());


        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            log.warn("Intento de registro con correo ya existente: {}", request.getCorreo());
            throw new IllegalArgumentException("El correo ya está registrado");
        }


        if (!request.getCorreo().endsWith(".edu.co") && !request.getCorreo().endsWith(".edu")) {
            throw new IllegalArgumentException("Debe usar un correo institucional");
        }

        Usuario usuario = Usuario.builder()
                .nombreCompleto(request.getNombreCompleto())
                .correo(request.getCorreo())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .rol(Rol.CLIENTE)
                .build();

        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario.getCorreo(), usuario.getRol().name());

        log.info("Usuario registrado exitosamente: {}", usuario.getCorreo());
        return new AuthResponse(token, usuario.getRol().name(), "Registro exitoso");
    }
}
