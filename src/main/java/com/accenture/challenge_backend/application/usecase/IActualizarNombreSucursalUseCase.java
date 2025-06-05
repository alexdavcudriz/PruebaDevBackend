package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.ActualizarNombreSucursalUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.infrastructure.document.SucursalDocument;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import com.accenture.challenge_backend.infrastructure.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_FRANQUICIA_NO_ENCONTRADA;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_LA_FRANQUICIA_NO_TIENE_SUCURSALES;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_SUCURSAL_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class IActualizarNombreSucursalUseCase implements ActualizarNombreSucursalUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> execute(String franquiciaId, String sucursalId, String nuevoNombre) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_FRANQUICIA_NO_ENCONTRADA)))
                .flatMap(franquicia -> {
                    List<SucursalDocument> sucursales = franquicia.getSucursales();
                    if (sucursales == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_LA_FRANQUICIA_NO_TIENE_SUCURSALES));
                    }

                    Optional<SucursalDocument> sucursalOpt = sucursales.stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst();

                    if (sucursalOpt.isEmpty()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_SUCURSAL_NO_ENCONTRADA));
                    }

                    sucursalOpt.get().setNombre(nuevoNombre);
                    return MapperUtils.toDTO(franquiciaRepository.save(franquicia));
                });
    }
}
