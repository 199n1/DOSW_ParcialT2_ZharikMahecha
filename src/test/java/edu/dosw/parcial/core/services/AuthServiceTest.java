package edu.dosw.parcial.core.services;

import edu.dosw.parcial.controller.dtos.request.RegisterRequest;
import edu.dosw.parcial.controller.dtos.response.AuthResponse;
import edu.dosw.parcial.core.models.Usuario;
import edu.dosw.parcial.core.models.enums.Rol;
import edu.dosw.parcial.persistence.repositories.UsuarioRepository;
import edu.dosw.parcial.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest request;

    @BeforeEach
    void setUp() {
        request = new RegisterRequest();
        request.setNombreCompleto("Juan Perez");
        request.setCorreo("juan@uni.edu.co");
        request.setContrasena("123456");
    }

    @Test
    void deberiaRegistrarUsuarioExitosamente() {
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(jwtService.generateToken(any(), any())).thenReturn("token123");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals("CLIENTE", response.getRol());
        assertEquals("Registro exitoso", response.getMensaje());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deberieFallarSiCorreoYaExiste() {
        when(usuarioRepository.existsByCorreo("juan@uni.edu.co")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> authService.register(request));

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deberieFallarSiCorreoNoEsInstitucional() {
        request.setCorreo("juan@gmail.com");
        when(usuarioRepository.existsByCorreo(any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> authService.register(request));

        verify(usuarioRepository, never()).save(any());
    }
}