package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class UseCaseEliminar implements Function<String, Mono<Void>> {

    private final RepositoryRecurso repositoryRecurso;
    private final MapperUtils mapperUtils;

    @Autowired
    public UseCaseEliminar(RepositoryRecurso repositoryRecurso, MapperUtils mapperUtils) {
        this.repositoryRecurso = repositoryRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<Void> apply(String id) {
        return repositoryRecurso.deleteById(id)
                .switchIfEmpty(Mono.empty());
    }
}
