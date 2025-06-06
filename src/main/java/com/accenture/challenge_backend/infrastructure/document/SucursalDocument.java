package com.accenture.challenge_backend.infrastructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDocument {
    private String id;
    private String nombre;
    private List<ProductoDocument> productos;
}
