package com.sofka.BibliotecaWebFlux.useCases;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class UseCaseActualizar implements SaveRecurso{

    private final RepositoryRecurso repositoryRecurso;
    private final MapperUtils mapperUtils;

    public UseCaseActualizar(RepositoryRecurso repositoryRecurso, MapperUtils mapperUtils) {
        this.repositoryRecurso = repositoryRecurso;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Mono<String> apply(DtoRecurso dtoRecurso) {
        return repositoryRecurso.save(mapperUtils
                .mapperToRecurso(dtoRecurso.getId())
                    .apply(dtoRecurso))
                .map(Recurso::getId);
    }
}
