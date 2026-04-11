package edu.dosw.parcial.controller.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String rol;
    private String mensaje;
}