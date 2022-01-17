package com.sofka.BibliotecaWebFlux.repository;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RepositoryRecurso extends ReactiveMongoRepository<Recurso, String> {
    Flux<Recurso> findByTipo(String tipo);
    Flux<Recurso> findByTematica(String tematica);
}
