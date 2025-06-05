package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.AgregarSucursalUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.SucursalDTO;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import com.accenture.challenge_backend.infrastructure.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_FRANQUICIA_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class IAgregarSucursalUseCase implements AgregarSucursalUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> execute(String franquiciaId, SucursalDTO request) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_FRANQUICIA_NO_ENCONTRADA)))
                .flatMap(franquiciaDocument -> {
                    if (franquiciaDocument.getSucursales() == null) {
                        franquiciaDocument.setSucursales(new ArrayList<>());
                    }
                    request.setId(UUID.randomUUID().toString());
                    Optional.ofNullable(request.getProductos())
                            .orElse(List.of())
                            .forEach(productoDTO -> productoDTO.setId(UUID.randomUUID().toString()));
                    franquiciaDocument.getSucursales().add(MapperUtils.fromDTO(request));
                    return MapperUtils.toDTO(franquiciaRepository.save(franquiciaDocument));
                });
    }
}
