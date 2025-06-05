package com.accenture.challenge_backend.infrastructure.util;

import com.accenture.challenge_backend.domain.model.FranquiciaDTO;
import com.accenture.challenge_backend.domain.model.ProductoDTO;
import com.accenture.challenge_backend.domain.model.SucursalDTO;
import com.accenture.challenge_backend.infrastructure.document.FranquiciaDocument;
import com.accenture.challenge_backend.infrastructure.document.ProductoDocument;
import com.accenture.challenge_backend.infrastructure.document.SucursalDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MapperUtils {

    public static FranquiciaDocument fromDTO(FranquiciaDTO dto) {
        if (dto == null) return null;
        return FranquiciaDocument.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .sucursales(
                        dto.getSucursales() == null ? null :
                                dto.getSucursales().stream()
                                        .map(MapperUtils::fromDTO)
                                        .toList()
                )
                .build();
    }

    public static FranquiciaDTO toDTO(FranquiciaDocument doc) {
        if (doc == null) return null;
        return FranquiciaDTO.builder()
                .id(doc.getId())
                .nombre(doc.getNombre())
                .sucursales(
                        doc.getSucursales() == null ? null :
                                doc.getSucursales().stream()
                                        .map(MapperUtils::toDTO)
                                        .toList()
                )
                .build();
    }

    public static Mono<FranquiciaDTO> toDTO(Mono<FranquiciaDocument> monoDoc) {
        return monoDoc.map(MapperUtils::toDTO);
    }

    public static Flux<FranquiciaDTO> toDTO(Flux<FranquiciaDocument> fluxDoc) {
        return fluxDoc.map(MapperUtils::toDTO);
    }

    public static SucursalDocument fromDTO(SucursalDTO dto) {
        if (dto == null) return null;
        return SucursalDocument.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .productos(
                        dto.getProductos() == null ? null :
                                dto.getProductos().stream()
                                        .map(MapperUtils::fromDTO)
                                        .toList()
                )
                .build();
    }

    public static SucursalDTO toDTO(SucursalDocument doc) {
        if (doc == null) return null;
        return SucursalDTO.builder()
                .id(doc.getId())
                .nombre(doc.getNombre())
                .productos(
                        doc.getProductos() == null ? null :
                                doc.getProductos().stream()
                                        .map(MapperUtils::toDTO)
                                        .toList()
                )
                .build();
    }

    public static ProductoDocument fromDTO(ProductoDTO dto) {
        if (dto == null) return null;
        return ProductoDocument.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .stock(dto.getStock())
                .build();
    }

    public static ProductoDTO toDTO(ProductoDocument doc) {
        if (doc == null) return null;
        return ProductoDTO.builder()
                .id(doc.getId())
                .nombre(doc.getNombre())
                .stock(doc.getStock())
                .build();
    }

    public static Mono<ProductoDTO> toDTOMonoP(Mono<ProductoDocument> monoDoc) {
        return monoDoc.map(MapperUtils::toDTO);
    }

}
