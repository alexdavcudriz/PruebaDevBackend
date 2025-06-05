package com.accenture.challenge_backend.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuerySucursalDTO {
    private String idSucursal;
    private String nombreSucursal;
    private String idProducto;
    private String nombreProducto;
    private int stock;
}
