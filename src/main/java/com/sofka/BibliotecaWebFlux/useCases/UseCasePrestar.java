package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.function.Function;

@Service
@Validated
public class UseCasePrestar implements Function<String, Mono<String>> {

    private final RepositoryRecurso repositoryRecurso;

    @Autowired
    public UseCasePrestar(RepositoryRecurso repositoryRecurso) {
        this.repositoryRecurso = repositoryRecurso;
    }

    @Override
    public Mono<String> apply(String id) {
        return repositoryRecurso.findById(id)
                .flatMap(recurso -> {
                    if (recurso.isDisponible()) {
                        recurso.setDisponible(false);
                        recurso.setFechaPrestamo(LocalDate.now());
                        return repositoryRecurso.save(recurso)
                                .thenReturn("El recurso ha sido prestado");
                    }
                    return Mono.just("El recurso ya se encuentra prestado desde el: " + recurso.getFechaPrestamo());
                });
    }
}