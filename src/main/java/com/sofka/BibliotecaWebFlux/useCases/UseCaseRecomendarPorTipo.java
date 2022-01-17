package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Service
@Validated
public class UseCaseRecomendarPorTipo implements Function<String, Flux<DtoRecurso>> {

    private final RepositoryRecurso repositoryRecurso;
    private final MapperUtils mapperUtils;

    @Autowired
    public UseCaseRecomendarPorTipo(RepositoryRecurso repositoryRecurso, MapperUtils mapperUtils) {
        this.repositoryRecurso = repositoryRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<DtoRecurso> apply(String tipo) {
        return repositoryRecurso.findByTipo(tipo)
                .map(mapperUtils.mapEntityToRecursoDTO());
    }
}
