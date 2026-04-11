package edu.dosw.parcial.core.services;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
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

        // Validación negocio: correo ya registrado
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            log.warn("Intento de registro con correo ya existente: {}", request.getCorreo());
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // Validación negocio: correo institucional
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
