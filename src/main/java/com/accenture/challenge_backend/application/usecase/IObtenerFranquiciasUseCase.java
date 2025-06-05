package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.ObtenerFranquiciasUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import com.accenture.challenge_backend.infrastructure.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class IObtenerFranquiciasUseCase implements ObtenerFranquiciasUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Flux<FranquiciaDTO> execute() {
        return MapperUtils.toDTO(franquiciaRepository.findAll());
    }
}
