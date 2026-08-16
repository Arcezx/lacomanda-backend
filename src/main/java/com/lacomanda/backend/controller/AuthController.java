package com.lacomanda.backend.controller;
import com.lacomanda.backend.dto.LoginRequestDTO;
import com.lacomanda.backend.dto.LoginResponseDTO;
import com.lacomanda.backend.entity.Usuario;
import com.lacomanda.backend.exception.NegocioException;
import com.lacomanda.backend.repository.UsuarioRepository;
import com.lacomanda.backend.security.JwtService;
import com.lacomanda.backend.security.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RateLimiterService rateLimiterService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto, HttpServletRequest request) {
        String ip = obtenerIp(request);

        if (!rateLimiterService.permitir(ip)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Demasiados intentos. Inténtalo de nuevo en un minuto.");
        }

        Usuario usuario = usuarioRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new NegocioException("Usuario o contraseña incorrectos"));
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new NegocioException("Usuario o contraseña incorrectos");
        }
        String token = jwtService.generarToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(
                token, usuario.getUsername(), usuario.getNombre(), usuario.getRol()
        ));
    }

    private String obtenerIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}