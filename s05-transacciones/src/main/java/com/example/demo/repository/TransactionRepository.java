package com.example.demo.repository;
import com.example.demo.document.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import java.util.List;

/**
 * Interfaz que define un repositorio para operaciones con las transacciones.
 * */
public interface TransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
    /**
     * Busca y devuelve una lista de transacciones para un cliente específico.
     * @param clientId El identificador del cliente para las transacciones.
     * @return Una lista de transacciones asociadas al cliente.
     */
    List<TransactionEntity> findByClientId(String clientId);

    /**
     * Busca y devuelve una lista de transacciones para un cliente específico.
     * @param sourceAccountNumber variable de sourceAccountNumber.
     * @return Una lista de transacciones TransactionEntity.
     */
    List<TransactionEntity> findBySourceNumber(String sourceAccountNumber);
}

