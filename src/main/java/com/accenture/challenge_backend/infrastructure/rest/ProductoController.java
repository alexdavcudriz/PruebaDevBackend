package com.accenture.challenge_backend.infrastructure.rest;

import com.accenture.challenge_backend.application.port.ActualizarNombreProductoUseCase;
import com.accenture.challenge_backend.application.port.ActualizarStockProductoUseCase;
import com.accenture.challenge_backend.application.port.AgregarProductoUseCase;
import com.accenture.challenge_backend.application.port.EliminarProductoSucursalUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.ProductoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/franquicias")
@Tag(name = "Productos", description = "API para gestión de productos")
@RequiredArgsConstructor
public class ProductoController {

    private final AgregarProductoUseCase agregarProductoUseCase;
    private final EliminarProductoSucursalUseCase eliminarProductoSucursalUseCase;
    private final ActualizarStockProductoUseCase actualizarStockProductoUseCase;
    private final ActualizarNombreProductoUseCase actualizarNombreProductoUseCase;

    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar producto", description = "Agrega un producto a una sucursal especifica")
    public Mono<FranquiciaDTO> agregarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @RequestBody ProductoDTO request) {
        return agregarProductoUseCase.execute(franquiciaId, sucursalId, request);
    }

    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto de una sucursal especifica")
    public Mono<FranquiciaDTO> eliminarProductoDeSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId) {
        return eliminarProductoSucursalUseCase.execute(franquiciaId, sucursalId, productoId);
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock")
    @Operation(summary = "Actualizar stock", description = "Actualiza el stock de un producto de una sucursal especifica")
    public Mono<FranquiciaDTO> actualizarStockProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @RequestBody ProductoDTO request) {
        return actualizarStockProductoUseCase.execute(franquiciaId, sucursalId, productoId, request.getStock());
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    @Operation(summary = "Actualizar nombre producto", description = "Actualiza el nombre de un producto específico")
    public Mono<ProductoDTO> actualizarNombreProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @RequestBody ProductoDTO request) {
        return actualizarNombreProductoUseCase.execute(franquiciaId, sucursalId, productoId, request.getNombre());
    }
}
