package com.accenture.challenge_backend.infrastructure.rest;

import com.accenture.challenge_backend.application.port.ActualizarNombreSucursalUseCase;
import com.accenture.challenge_backend.application.port.AgregarSucursalUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.SucursalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class SucursalController {

    private final AgregarSucursalUseCase agregarSucursalUseCase;
    private final ActualizarNombreSucursalUseCase actualizarNombreSucursalUseCase;

    @PostMapping("/{id}/sucursales")
    public Mono<FranquiciaDTO> agregarSucursal(
            @PathVariable String id,
            @RequestBody SucursalDTO request) {
        return agregarSucursalUseCase.execute(id, request);
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}")
    public Mono<FranquiciaDTO> actualizarNombreSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @RequestBody SucursalDTO request) {
        return actualizarNombreSucursalUseCase.execute(franquiciaId, sucursalId, request.getNombre());
    }
}
