package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.ActualizarNombreFranquiciaUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import com.accenture.challenge_backend.infrastructure.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_FRANQUICIA_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class IActualizarNombreFranquiciaUseCase implements ActualizarNombreFranquiciaUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> execute(String id, String nuevoNombre) {
        return franquiciaRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_FRANQUICIA_NO_ENCONTRADA)))
                .flatMap(franquicia -> {
                    franquicia.setNombre(nuevoNombre);
                    return MapperUtils.toDTO(franquiciaRepository.save(franquicia));
                });
    }
}
