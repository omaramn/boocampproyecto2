package com.example.demo.entitys;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa una entidad de ClientEntity.
 */
@Data
@Builder
@Document(collection = "clients")
public class ClientEntity {
    /**
     * Identificador único de la ClientEntity.
     */
    @Id
    private String id;

    /**
     * Identificador único del name.
     */
    private String name;

    /**
     * Identificador único del dni.
     */
    private String dni;

    /**
     * Identificador único del type.
     */
    private ClientType type;

    /**
     * Identificador único del ClientType.
     */
    public enum ClientType {
        /**
         * Identificador único del PERSONAL.
         */
        PERSONAL,
        /**
         * Identificador único del EMPRESARIAL.
         */
        EMPRESARIAL
    }
}

