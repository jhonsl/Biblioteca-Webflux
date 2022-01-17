package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class UseCaseListar implements Supplier<Flux<DtoRecurso>> {

    private final RepositoryRecurso repositoryRecurso;
    private final MapperUtils mapperUtils;

    @Autowired
    public UseCaseListar(RepositoryRecurso repositoryRecurso, MapperUtils mapperUtils) {
        this.repositoryRecurso = repositoryRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<DtoRecurso> get() {
        return repositoryRecurso.findAll()
                .map(mapperUtils.mapEntityToRecursoDTO());
    }
}
