package com.accenture.challenge_backend.application.port;

import com.accenture.challenge_backend.domain.model.ProductoDTO;
import reactor.core.publisher.Mono;

public interface ActualizarNombreProductoUseCase {
    Mono<ProductoDTO> execute(String franquiciaId, String sucursalId, String productoId, String nuevoNombre);
}
