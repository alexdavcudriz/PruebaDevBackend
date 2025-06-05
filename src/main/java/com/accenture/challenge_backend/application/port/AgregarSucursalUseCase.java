package com.accenture.challenge_backend.application.port;

import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.SucursalDTO;
import reactor.core.publisher.Mono;

public interface AgregarSucursalUseCase {
    Mono<FranquiciaDTO> execute(String franquiciaId, SucursalDTO request);
}
