package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@Validated
public class UseCaseDevolver implements Function<String, Mono<String>> {

    private final RepositoryRecurso repositoryRecurso;

    @Autowired
    public UseCaseDevolver(RepositoryRecurso repositoryRecurso) {
        this.repositoryRecurso = repositoryRecurso;
    }

    @Override
    public Mono<String> apply(String id) {
        return repositoryRecurso.findById(id)
                .flatMap(recurso -> {
                    if(recurso.isDisponible()){
                        return Mono.just("El recurso no ha sido prestado");
                    }
                    recurso.setDisponible(true);
                    recurso.setFechaPrestamo(null);
                    return repositoryRecurso.save(recurso)
                            .thenReturn("El recurso ha sido devuelto");
                });
    }
}
