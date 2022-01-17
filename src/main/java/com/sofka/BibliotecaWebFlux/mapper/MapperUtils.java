package com.sofka.BibliotecaWebFlux.mapper;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<DtoRecurso, Recurso> mapperToRecurso(String id){
        return updateRecurso -> {
            var recurso = new Recurso();
            recurso.setId(id);
            recurso.setTitulo(updateRecurso.getTitulo());
            recurso.setAutor(updateRecurso.getAutor());
            recurso.setDisponible(updateRecurso.isDisponible());
            recurso.setTematica(updateRecurso.getTematica());
            recurso.setTipo(updateRecurso.getTipo());
            recurso.setFechaPrestamo(updateRecurso.getFechaPrestamo());

            return recurso;
        };
    }

    public Function<Recurso, DtoRecurso> mapEntityToRecursoDTO(){
        return entity -> new DtoRecurso(
                entity.getId(),
                entity.getTitulo(),
                entity.getAutor(),
                entity.getTematica(),
                entity.getTipo(),
                entity.isDisponible(),
                entity.getFechaPrestamo());
    }
}
