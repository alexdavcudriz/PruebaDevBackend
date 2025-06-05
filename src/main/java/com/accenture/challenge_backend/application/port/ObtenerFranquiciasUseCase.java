package com.accenture.challenge_backend.application.port;

import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import reactor.core.publisher.Flux;

public interface ObtenerFranquiciasUseCase {
    Flux<FranquiciaDTO> execute();
}
