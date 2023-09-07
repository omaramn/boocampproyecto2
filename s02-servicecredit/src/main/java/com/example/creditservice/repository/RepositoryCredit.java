package com.example.creditservice.repository;

import com.example.creditservice.document.CreditEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Interfaz que define un RepositoryCredit para CreditEntity.
 * */
@Component
public interface RepositoryCredit extends MongoRepository<CreditEntity, String> {
       /**
        * AccountMongoRepository.
        * @param s búsqueda por ObjectId.
        * @return un objeto CreditEntity.
        */
       Optional<CreditEntity> findById(ObjectId s);
}
