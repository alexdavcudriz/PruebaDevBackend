package com.accenture.challenge_backend.application.port;

import com.accenture.challenge_backend.domain.model.QuerySucursalDTO;
import reactor.core.publisher.Flux;

public interface ObtenerProductosMayorStockPorSucursalUseCase {
    Flux<QuerySucursalDTO> execute(String franquiciaId);
}
