package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class UseCaseDisponible implements Function<String, Mono<String>> {

    private final RepositoryRecurso repositoryRecurso;

    @Autowired
    public UseCaseDisponible(RepositoryRecurso repositoryRecurso) {
        this.repositoryRecurso = repositoryRecurso;
    }

    @Override
    public Mono<String> apply(String id) {
        return repositoryRecurso.findById(id)
                .map(recurso -> recurso.isDisponible() ? "El recurso esta disponible"
                                                        : "El recurso fue prestado el: " + recurso.getFechaPrestamo());
    }
}
