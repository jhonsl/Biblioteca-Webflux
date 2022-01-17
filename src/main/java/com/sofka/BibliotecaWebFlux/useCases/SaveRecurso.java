package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface SaveRecurso {
    public Mono<String> apply(DtoRecurso dtoRecurso);
}
