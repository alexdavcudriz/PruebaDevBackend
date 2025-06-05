package com.accenture.challenge_backend.application.port;

import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import reactor.core.publisher.Mono;

public interface EliminarProductoSucursalUseCase {
    Mono<FranquiciaDTO> execute(String franquiciaId, String sucursalId, String productoId);
}
