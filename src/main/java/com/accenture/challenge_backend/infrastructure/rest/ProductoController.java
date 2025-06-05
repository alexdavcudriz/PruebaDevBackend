package com.accenture.challenge_backend.infrastructure.rest;

import com.accenture.challenge_backend.application.port.ActualizarNombreProductoUseCase;
import com.accenture.challenge_backend.application.port.ActualizarStockProductoUseCase;
import com.accenture.challenge_backend.application.port.AgregarProductoUseCase;
import com.accenture.challenge_backend.application.port.EliminarProductoSucursalUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.ProductoDTO;
import com.accenture.challenge_backend.infrastructure.document.FranquiciaDocument;
import com.accenture.challenge_backend.infrastructure.document.ProductoDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class ProductoController {

    private final AgregarProductoUseCase agregarProductoUseCase;
    private final EliminarProductoSucursalUseCase eliminarProductoSucursalUseCase;
    private final ActualizarStockProductoUseCase actualizarStockProductoUseCase;
    private final ActualizarNombreProductoUseCase actualizarNombreProductoUseCase;

    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaDTO> agregarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @RequestBody ProductoDTO request) {
        return agregarProductoUseCase.execute(franquiciaId, sucursalId, request);
    }

    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    public Mono<FranquiciaDTO> eliminarProductoDeSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId) {
        return eliminarProductoSucursalUseCase.execute(franquiciaId, sucursalId, productoId);
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock")
    public Mono<FranquiciaDTO> actualizarStockProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @RequestBody ProductoDTO request) {
        return actualizarStockProductoUseCase.execute(franquiciaId, sucursalId, productoId, request.getStock());
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    public Mono<ProductoDTO> actualizarNombreProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @RequestBody ProductoDTO request) {
        return actualizarNombreProductoUseCase.execute(franquiciaId, sucursalId, productoId, request.getNombre());
    }
}
