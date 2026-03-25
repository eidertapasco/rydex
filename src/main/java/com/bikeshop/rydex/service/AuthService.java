package com.bikeshop.rydex.service;

import com.bikeshop.rydex.dto.request.LoginRequest;
import com.bikeshop.rydex.dto.request.RegisterRequest;
import com.bikeshop.rydex.dto.response.AuthResponse;
import com.bikeshop.rydex.enums.RolUsuario;
import com.bikeshop.rydex.model.ClienteModel;
import com.bikeshop.rydex.repository.ClienteRepository;
import com.bikeshop.rydex.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        ClienteModel cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(request.getPassword(), cliente.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtUtil.generateToken(cliente.getEmail(), cliente.getRol().name());
        return buildAuthResponse(token, cliente);
    }

    public AuthResponse register(RegisterRequest request) {
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        if (clienteRepository.existsByDocumento(request.getDocumento())) {
            throw new RuntimeException("El documento ya está registrado");
        }

        ClienteModel cliente = new ClienteModel();
        cliente.setNombre(request.getNombre());
        cliente.setDocumento(request.getDocumento());
        cliente.setTelefono(request.getTelefono());
        cliente.setEmail(request.getEmail());
        cliente.setPassword(passwordEncoder.encode(request.getPassword()));
        cliente.setRol(RolUsuario.CLIENTE);

        clienteRepository.save(cliente);

        String token = jwtUtil.generateToken(cliente.getEmail(), cliente.getRol().name());
        return buildAuthResponse(token, cliente);
    }

    private AuthResponse buildAuthResponse(String token, ClienteModel cliente) {
        AuthResponse.ClienteResponse clienteResponse = new AuthResponse.ClienteResponse(
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDocumento(),
                cliente.getTelefono(),
                cliente.getRol().name()
        );
        return new AuthResponse(token, clienteResponse);
    }
}