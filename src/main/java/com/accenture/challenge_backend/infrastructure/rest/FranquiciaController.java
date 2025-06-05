package com.accenture.challenge_backend.infrastructure.rest;

import com.accenture.challenge_backend.application.port.ActualizarNombreFranquiciaUseCase;
import com.accenture.challenge_backend.application.port.CrearFranquiciaUseCase;
import com.accenture.challenge_backend.application.port.ObtenerFranquiciasUseCase;
import com.accenture.challenge_backend.application.port.ObtenerProductosMayorStockPorSucursalUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.infrastructure.document.FranquiciaDocument;
import com.accenture.challenge_backend.domain.model.QuerySucursalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final CrearFranquiciaUseCase crearFranquiciaUseCase;
    private final ObtenerFranquiciasUseCase obtenerFranquiciasUseCase;
    private final ObtenerProductosMayorStockPorSucursalUseCase obtenerProductosMayorStockPorSucursalUseCase;
    private final ActualizarNombreFranquiciaUseCase actualizarNombreFranquiciaUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaDTO> crearFranquicia(@RequestBody FranquiciaDTO request) {
        return crearFranquiciaUseCase.execute(request);
    }

    @GetMapping
    public Flux<FranquiciaDTO> obtenerFranquicias() {
        return obtenerFranquiciasUseCase.execute();
    }

    @GetMapping("/{id}/productos-mayor-stock")
    public Flux<QuerySucursalDTO> obtenerProductosMayorStock(@PathVariable String id) {
        return obtenerProductosMayorStockPorSucursalUseCase.execute(id);
    }

    @PatchMapping("/{id}")
    public Mono<FranquiciaDTO> actualizarNombreFranquicia(
            @PathVariable String id,
            @RequestBody FranquiciaDocument request) {
        return actualizarNombreFranquiciaUseCase.execute(id, request.getNombre());
    }
}
