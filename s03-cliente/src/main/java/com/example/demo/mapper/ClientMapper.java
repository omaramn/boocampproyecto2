package com.example.demo.mapper;

import com.example.demo.entitys.ClientEntity;
import com.example.demo.model.Client;
public class ClientMapper {
    /**
     * Método para ClientMapper.
     * @param clientDto parametro de Client.
     * @return ClientEntity.
     */
    public static ClientEntity dtoToEntity(final Client clientDto) {
        return ClientEntity.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .dni(clientDto.getDni())
                .type(dtoTypeToEntityType(clientDto.getType()))
                .build();
    }

    /**
     * Método para ClientMapper.
     * @param typeEnum parametro de Client.TypeEnum.
     * @return ClientEntity.ClientType.
     */
    private static ClientEntity.ClientType dtoTypeToEntityType(final Client.TypeEnum typeEnum) {
        if (typeEnum == null) {
            return null;
        }
        switch (typeEnum) {
            case PERSONAL:
                return ClientEntity.ClientType.PERSONAL;
            case EMPRESARIAL:
                return ClientEntity.ClientType.EMPRESARIAL;
            default:
                throw new IllegalArgumentException("Unexpected type: " + typeEnum);
        }
    }

    /**
     * Método para ClientMapper.
     * @param entity parametro de ClientEntity.
     * @return Client.
     */
    public static Client entityToDto(final ClientEntity entity) {
        return new Client()
                .id(entity.getId())
                .name(entity.getName())
                .dni(entity.getDni())
                .type(entityTypeToDtoType(entity.getType()));
    }

    /**
     * Método para ClientMapper.
     * @param clientType parametro de ClientEntity.ClientType.
     * @return Client.TypeEnum.
     */
    private static Client.TypeEnum entityTypeToDtoType(final ClientEntity.ClientType clientType) {
        if (clientType == null) {
            return null;
        }
        switch (clientType) {
            case PERSONAL:
                return Client.TypeEnum.PERSONAL;
            case EMPRESARIAL:
                return Client.TypeEnum.EMPRESARIAL;
            default:
                throw new IllegalArgumentException("Unexpected type: " + clientType);
        }
    }
}


