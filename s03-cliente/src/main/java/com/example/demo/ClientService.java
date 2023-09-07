package com.example.demo;

import com.example.demo.entitys.ClientEntity;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    /**
     * Para ClientRepository de la BD.
     */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Método para guardar una transacción.
     * @param clientDto parametro de Client.
     * @return Client.
     */
    public Client createClient(final Client clientDto) {
        ClientEntity entity = ClientMapper.dtoToEntity(clientDto);
        ClientEntity savedEntity = clientRepository.save(entity).block();
        return ClientMapper.entityToDto(savedEntity);
    }

    /**
     * Método para guardar una transacción.
     * @return List<Client>.
     */
    public List<Client> getAllClients() {
        List<ClientEntity> entities = clientRepository.findAll().collectList().block();
        return entities.stream().map(ClientMapper::entityToDto).collect(Collectors.toList());
    }

    /**
     * Método para guardar una transacción.
     * @param id variable strimg.
     * @return Client.
     */
    public Client getClientById(final String id) {
        if (!ObjectId.isValid(id)) {
            return null; // Aquí puedes decidir qué hacer. Devolver null, lanzar una excepción, etc.
        }
        ObjectId objectId = new ObjectId(id);
        ClientEntity entity = clientRepository.findById(objectId.toString()).block();
        if (entity == null) {
            return null; // O manejar de la forma que prefieras si el cliente no se encuentra.
        }
        return ClientMapper.entityToDto(entity);
    }

    /**
     * Método para guardar una transacción.
     * @param ids lista de string.
     * @return Client lista de clinentes.
     */
    public List<Client> bulkRetrieveClients(final List<String> ids) {
        List<ObjectId> objectIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
        List<ClientEntity> entities = clientRepository.findAllByGivenIds(objectIds).collectList().block();
        return entities.stream().map(ClientMapper::entityToDto).collect(Collectors.toList());
    }
}
