package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.AgregarProductoUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.ProductoDTO;
import com.accenture.challenge_backend.infrastructure.document.SucursalDocument;
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
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_SUCURSAL_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class IAgregarProductoUseCase implements AgregarProductoUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> execute(String franquiciaId, String sucursalId, ProductoDTO request) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_FRANQUICIA_NO_ENCONTRADA)))
                .flatMap(franquiciaDTO -> {
                    List<SucursalDocument> sucursales = franquiciaDTO.getSucursales();
                    if (sucursales == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_SUCURSAL_NO_ENCONTRADA));
                    }

                    Optional<SucursalDocument> sucursalOpt = sucursales.stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst();

                    if (sucursalOpt.isEmpty()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_SUCURSAL_NO_ENCONTRADA));
                    }

                    SucursalDocument sucursalDocument = sucursalOpt.get();
                    if (sucursalDocument.getProductos() == null) {
                        sucursalDocument.setProductos(new ArrayList<>());
                    }
                    request.setId(UUID.randomUUID().toString());
                    sucursalDocument.getProductos().add(MapperUtils.fromDTO(request));

                    return MapperUtils.toDTO(franquiciaRepository.save(franquiciaDTO));
                });
    }
}
