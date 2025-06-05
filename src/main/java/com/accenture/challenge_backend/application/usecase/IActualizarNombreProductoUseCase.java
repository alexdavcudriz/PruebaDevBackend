package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.ActualizarNombreProductoUseCase;
import com.accenture.challenge_backend.domain.model.ProductoDTO;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import com.accenture.challenge_backend.infrastructure.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IActualizarNombreProductoUseCase implements ActualizarNombreProductoUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Mono<ProductoDTO> execute(String franquiciaId, String sucursalId, String productoId, String nuevoNombre) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    var sucursalOpt = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst();
                    if (sucursalOpt.isEmpty()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Sucursal no encontrada"));
                    }

                    var sucursal = sucursalOpt.get();

                    var productoOpt = sucursal.getProductos().stream()
                            .filter(p -> p.getId().equals(productoId))
                            .findFirst();

                    if (productoOpt.isEmpty()) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
                    }

                    var producto = productoOpt.get();
                    producto.setNombre(nuevoNombre);

                    return MapperUtils.toDTOMonoP(franquiciaRepository.save(franquicia)
                            .thenReturn(producto));
                });
    }
}
