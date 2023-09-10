    package com.example.demo;
    import com.example.demo.document.ClientEntity;
    import com.example.demo.document.TipoClienteEntity;
    import com.example.demo.document.PerfilEntity;
    import com.example.demo.mapper.ClientMapper;
    import com.example.demo.model.Client;
    import com.example.demo.repository.ClientRepository;
    import com.example.demo.repository.PerfilRepository;
    import com.example.demo.repository.TipoClienteRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.bson.types.ObjectId;
    import java.util.List;
    import java.util.function.Predicate;
    import java.util.stream.Collectors;

    @Service
    public class ClientService {
        /**
         * Para ClientRepository de la BD.
         */
        @Autowired
        private ClientRepository clientRepository;

        /**
         * Para TipoClienteRepository de la BD.
         */
        @Autowired
        private TipoClienteRepository tipoClienteRepository;

        /**
         * Para PerfilRepository de la BD.
         */
        @Autowired
        private PerfilRepository perfilRepository;

        /**
         * Método para guardar una transacción.
         * @param clientDto parametro de Client.
         * @return Client.
         */
        public Client createClient(final Client clientDto) {
            TipoClienteEntity tipoClienteEntity = tipoClienteRepository.findByNombre(clientDto.getTipoCliente().getNombre());
            if (tipoClienteEntity == null) {
                tipoClienteEntity = createOrGetTipoClienteEntity(clientDto.getTipoCliente().getNombre());
            }
            clientDto.setTipoCliente(ClientMapper.entityTypeToDtoType(tipoClienteEntity));

            ClientEntity entityToSave = ClientMapper.dtoToEntity(clientDto);
            ClientEntity savedEntity = clientRepository.save(entityToSave);
            return ClientMapper.entityToDto(savedEntity);
        }

        /**
         * Método para guardar una transacción.
         * @param nombreTipoCliente variable String.
         * @return TipoClienteEntity.
         */
        private TipoClienteEntity createOrGetTipoClienteEntity(final String nombreTipoCliente) {
            Predicate<String> isPersonal = "personal"::equalsIgnoreCase;
            Predicate<String> isEmpresarial = "empresarial"::equalsIgnoreCase;

            if (!isPersonal.or(isEmpresarial).test(nombreTipoCliente)) {
                throw new IllegalArgumentException("El valor de nombreTipoCliente no es válido.");
            }
            String nombrePerfil = isEmpresarial.test(nombreTipoCliente) ? "pyme" : "vip";

            PerfilEntity existingPerfilEntity = perfilRepository.findByNombre(nombrePerfil);
            if (existingPerfilEntity == null) {
                existingPerfilEntity = createPerfilEntity(nombrePerfil);
            }

            TipoClienteEntity tipoClienteEntity = TipoClienteEntity.builder()
                    .nombre(nombreTipoCliente)
                    .perfil(existingPerfilEntity)
                    .build();

            return tipoClienteRepository.save(tipoClienteEntity);
        }

        /**
         * Método para guardar una transacción.
         * @param nombrePerfil variable String.
         * @return PerfilEntity.
         */
        private PerfilEntity createPerfilEntity(final String nombrePerfil) {
            PerfilEntity newPerfilEntity = PerfilEntity.builder().nombre(nombrePerfil).build();
            return perfilRepository.save(newPerfilEntity);
        }

        /**
         * Método para guardar una transacción.
         * @return List<Client>.
         */
        public List<Client> getAllClients() {
            List<ClientEntity> entities = clientRepository.findAll();
            return entities.stream().map(ClientMapper::entityToDto).collect(Collectors.toList());
        }

        /**
         * Método para guardar una transacción.
         * @param id variable strimg.
         * @return Client.
         */
        public Client getClientById(final String id) {
            if (!ObjectId.isValid(id)) {
                return null;
            }

            return clientRepository.findById(id)
                    .map(ClientMapper::entityToDto)
                    .orElse(null);
        }

        /**
         * Método para guardar una transacción.
         * @param ids lista de string.
         * @return Client lista de clinentes.
         */
        public List<Client> bulkRetrieveClients(final List<String> ids) {
            List<ObjectId> objectIds = ids.stream().map(ObjectId::new).collect(Collectors.toList());
            List<ClientEntity> entities = clientRepository.findAllByGivenIds(objectIds);
            return entities.stream().map(ClientMapper::entityToDto).collect(Collectors.toList());
        }
    }
