package com.accenture.challenge_backend.application.usecase;

import com.accenture.challenge_backend.application.port.ObtenerProductosMayorStockPorSucursalUseCase;
import com.accenture.challenge_backend.infrastructure.document.ProductoDocument;
import com.accenture.challenge_backend.domain.model.QuerySucursalDTO;
import com.accenture.challenge_backend.infrastructure.document.SucursalDocument;
import com.accenture.challenge_backend.infrastructure.repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_FRANQUICIA_NO_ENCONTRADA;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_LA_FRANQUICIA_NO_TIENE_SUCURSALES;
import static com.accenture.challenge_backend.infrastructure.util.ConstantsUtils.MESSAGE_NO_SE_ENCONTRARON_PRODUCTOS_CON_STOCK_EN_LAS_SUCURSALES;

@Service
@RequiredArgsConstructor
public class IObtenerProductosMayorStockPorSucursalUseCase implements ObtenerProductosMayorStockPorSucursalUseCase {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public Flux<QuerySucursalDTO> execute(String franquiciaId) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_FRANQUICIA_NO_ENCONTRADA)))
                .flatMapMany(franquicia -> {
                    List<SucursalDocument> sucursales = franquicia.getSucursales();
                    if (sucursales == null || sucursales.isEmpty()) {
                        return Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_LA_FRANQUICIA_NO_TIENE_SUCURSALES));
                    }

                    return Flux.fromIterable(sucursales)
                            .flatMap(sucursal -> {
                                List<ProductoDocument> productos = sucursal.getProductos();
                                if (productos == null || productos.isEmpty()) {
                                    return Mono.empty();
                                }

                                return productos.stream()
                                        .max(Comparator.comparingInt(ProductoDocument::getStock))
                                        .map(productoConMasStock -> Mono.just(
                                                QuerySucursalDTO.builder()
                                                        .idSucursal(sucursal.getId())
                                                        .nombreSucursal(sucursal.getNombre())
                                                        .idProducto(productoConMasStock.getId())
                                                        .nombreProducto(productoConMasStock.getNombre())
                                                        .stock(productoConMasStock.getStock())
                                                        .build()
                                        ))
                                        .orElse(Mono.empty());
                            })
                            .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MESSAGE_NO_SE_ENCONTRARON_PRODUCTOS_CON_STOCK_EN_LAS_SUCURSALES)));
                });
    }
}
