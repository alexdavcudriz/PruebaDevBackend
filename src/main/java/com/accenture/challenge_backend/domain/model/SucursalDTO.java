package com.accenture.challenge_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {
    private String id;
    private String nombre;
    private List<ProductoDTO> productos;
}
