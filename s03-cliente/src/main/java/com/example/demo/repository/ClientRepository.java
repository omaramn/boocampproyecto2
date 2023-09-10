package com.example.demo.repository;
import com.example.demo.document.ClientEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

/**
 * Interfaz que define un ReactiveMongoRepository para ClientRepository.
 * */
public interface ClientRepository extends MongoRepository<ClientEntity, String> {
    /**
     * AccountMongoRepository.
     * @param ids búsqueda por id.
     * @return una lista de ClientEntity.
     */
    @Query("{ '_id': { $in: ?0 } }")
    List<ClientEntity> findAllByGivenIds(List<ObjectId> ids);
}
