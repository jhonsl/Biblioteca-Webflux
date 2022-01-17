package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;


@Service
@Validated
public class UseCaseRecomendarPorTematica implements Function<String, Flux<DtoRecurso>> {

    private final RepositoryRecurso repositoryRecurso;
    private final MapperUtils mapperUtils;

    public UseCaseRecomendarPorTematica(RepositoryRecurso repositoryRecurso, MapperUtils mapperUtils) {
        this.repositoryRecurso = repositoryRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<DtoRecurso> apply(String tematica) {
        return repositoryRecurso.findByTematica(tematica)
                .map(mapperUtils.mapEntityToRecursoDTO());
    }
}
