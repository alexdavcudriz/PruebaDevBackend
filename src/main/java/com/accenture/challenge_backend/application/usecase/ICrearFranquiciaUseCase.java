package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.CrearFranquiciaUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import com.accenture.challenge_backend.infrastructure.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ICrearFranquiciaUseCase implements CrearFranquiciaUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> execute(FranquiciaDTO request) {
        request.setId(UUID.randomUUID().toString());
        Optional.ofNullable(request.getSucursales())
                .orElse(List.of())
                .forEach(sucursalDTO -> {
                    sucursalDTO.setId(UUID.randomUUID().toString());
                    Optional.ofNullable(sucursalDTO.getProductos())
                            .orElse(List.of())
                            .forEach(productoDTO -> productoDTO.setId(UUID.randomUUID().toString()));
                });
        return MapperUtils.toDTO(franquiciaRepository.save(MapperUtils.fromDTO(request)));
    }
}
