package com.example.demo.repository;
import com.example.demo.document.PerfilEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interfaz que define un ReactiveMongoRepository para PerfilRepository.
 * */
public interface PerfilRepository extends MongoRepository<PerfilEntity, String> {
    /**
     * AccountMongoRepository.
     * @param nombre búsqueda por nombre.
     * @return PerfilEntity.
     */
    PerfilEntity findByNombre(String nombre);
}
