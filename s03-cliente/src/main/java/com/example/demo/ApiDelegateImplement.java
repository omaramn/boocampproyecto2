package com.example.demo;
import com.example.demo.api.ClientsApiDelegate;
import com.example.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;
@Component
public class Apidelegateimplement implements ClientsApiDelegate {
    /**
     * Para acceder a clientService.
     */
    @Autowired
    private ClientService clientService;

    /**
     * Método para guardar una transacción.
     * @return ClientsApiDelegate.
     */
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ClientsApiDelegate.super.getRequest();
    }

    /**
     * Método para guardar una transacción.
     * @param client parametro de Client.
     * @return Client.
     */
    @Override
    public ResponseEntity<Void> createClient(final Client client) {
        Client createdClient = clientService.createClient(client);
        if (createdClient != null) {
            return ResponseEntity.created(URI.create("/clients/" + createdClient.getId())).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Método para guardar una transacción.
     * @return List<Client>.
     */
    @Override
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Método para guardar una transacción.
     * @param clientId variable Client.
     * @return Client.
     */
    @Override
    public ResponseEntity<Client> getClientById(final String clientId) {
        Client client = clientService.getClientById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    /**
     * Método para guardar una transacción.
     * @param ids variable String.
     * @return Client lista de clinentes.
     */
    @Override
    public ResponseEntity<List<Client>> bulkRetrieveClients(final List<String> ids) {
        List<Client> clients = clientService.bulkRetrieveClients(ids);
        if (clients != null && !clients.isEmpty()) {
            return ResponseEntity.ok(clients);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
