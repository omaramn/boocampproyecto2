package com.example.demo.repository;
import com.example.demo.document.TipoClienteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interfaz que define un ReactiveMongoRepository para TipoClienteRepository.
 * */
public interface TipoClienteRepository extends MongoRepository<TipoClienteEntity, String> {
    /**
     * AccountMongoRepository.
     * @param nombre búsqueda por nombre.
     * @return TipoClienteEntity.
     */
    TipoClienteEntity findByNombre(String nombre);
}
