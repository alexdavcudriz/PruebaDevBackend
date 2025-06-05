package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.EliminarProductoSucursalUseCase;
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

import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_FRANQUICIA_NO_ENCONTRADA;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_PRODUCTO_NO_ENCONTRADO;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_SUCURSAL_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class IEliminarProductoSucursalUseCase implements EliminarProductoSucursalUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> execute(String franquiciaId, String sucursalId, String productoId) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_FRANQUICIA_NO_ENCONTRADA)))
                .flatMap(franquiciaDTO -> {
                    List<SucursalDocument> sucursales = franquiciaDTO.getSucursales();
                    SucursalDocument sucursalDocument = sucursales.stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst()
                            .orElse(null);

                    if (sucursalDocument == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_SUCURSAL_NO_ENCONTRADA));
                    }

                    boolean removed = sucursalDocument.getProductos().removeIf(p -> p.getId().equals(productoId));

                    if (!removed) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_PRODUCTO_NO_ENCONTRADO));
                    }

                    return MapperUtils.toDTO(franquiciaRepository.save(franquiciaDTO));
                });
    }
}
