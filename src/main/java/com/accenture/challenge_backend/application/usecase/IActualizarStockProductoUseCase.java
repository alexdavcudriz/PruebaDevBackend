package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.ActualizarStockProductoUseCase;
import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.infrastructure.document.ProductoDocument;
import com.accenture.challenge_backend.infrastructure.document.SucursalDocument;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import com.accenture.challenge_backend.infrastructure.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_FRANQUICIA_NO_ENCONTRADA;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_PRODUCTO_NO_ENCONTRADO;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_STOCK_INVALIDO;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_SUCURSAL_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor
public class IActualizarStockProductoUseCase implements ActualizarStockProductoUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<FranquiciaDTO> execute(String franquiciaId, String sucursalId, String productoId, int nuevoStock) {
        if (nuevoStock < 0) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, MESSAGE_STOCK_INVALIDO));
        }

        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_FRANQUICIA_NO_ENCONTRADA)))
                .flatMap(franquiciaDocument -> {
                    SucursalDocument sucursalDocument = franquiciaDocument.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst()
                            .orElse(null);

                    if (sucursalDocument == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_SUCURSAL_NO_ENCONTRADA));
                    }

                    ProductoDocument productoDocument = sucursalDocument.getProductos().stream()
                            .filter(p -> p.getId().equals(productoId))
                            .findFirst()
                            .orElse(null);

                    if (productoDocument == null) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_PRODUCTO_NO_ENCONTRADO));
                    }

                    productoDocument.setStock(nuevoStock);
                    return MapperUtils.toDTO(franquiciaRepository.save(franquiciaDocument));
                });
    }
}
