package com.accenture.challenge_backend.application.port;

import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.ProductoDTO;
import reactor.core.publisher.Mono;

public interface AgregarProductoUseCase {
    Mono<FranquiciaDTO> execute(String franquiciaId, String sucursalId, ProductoDTO request);
}
