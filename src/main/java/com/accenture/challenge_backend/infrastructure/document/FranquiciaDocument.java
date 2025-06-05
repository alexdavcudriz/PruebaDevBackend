package com.accenture.challenge_backend.infrastructure.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "franquicias")
public class FranquiciaDocument {
    @Id
    private String id;
    private String nombre;
    private List<SucursalDocument> sucursales;
}
