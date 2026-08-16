package com.lacomanda.backend.controller;

import com.lacomanda.backend.entity.Configuracion;
import com.lacomanda.backend.repository.ConfiguracionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuracion")
@RequiredArgsConstructor
public class ConfiguracionController {

    private final ConfiguracionRepository configuracionRepository;

    @GetMapping("/{clave}")
    public ResponseEntity<String> obtener(@PathVariable String clave) {
        return configuracionRepository.findById(clave)
                .map(c -> ResponseEntity.ok(c.getValor()))
                .orElse(ResponseEntity.ok("http://localhost:8100"));
    }

    @PutMapping("/{clave}")
    public ResponseEntity<Void> guardar(@PathVariable String clave, @RequestBody String valor) {
        Configuracion config = configuracionRepository.findById(clave)
                .orElse(new Configuracion());
        config.setClave(clave);
        config.setValor(valor);
        configuracionRepository.save(config);
        return ResponseEntity.ok().build();
    }
}