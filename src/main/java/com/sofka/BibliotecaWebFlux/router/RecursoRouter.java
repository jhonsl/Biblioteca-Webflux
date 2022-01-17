package com.sofka.BibliotecaWebFlux.router;

import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RecursoRouter {

    @Bean
    public RouterFunction<ServerResponse> createQuestion(UseCaseCrear useCaseCrear){
        return route(POST("/añadir").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DtoRecurso.class)
                        .flatMap(questionDTO -> useCaseCrear.apply(questionDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.TEXT_PLAIN)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> update(UseCaseActualizar useCaseActualizar){
        return route(PUT("/editar").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(DtoRecurso.class)
                        .flatMap(questionDTO -> useCaseActualizar.apply(questionDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.TEXT_PLAIN)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getAll(UseCaseListar useCaseListar){
        return route(GET("/recursos").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCaseListar.get(), DtoRecurso.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> delete(UseCaseEliminar UseCaseEliminar){
        return route(DELETE("/eliminar/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(UseCaseEliminar.apply(request.pathVariable("id")), Void.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> getDisponible(UseCaseDisponible useCaseDisponible){
        return route(GET("/estado/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCaseDisponible.apply(request.pathVariable("id")), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> lendRecurso(UseCasePrestar useCasePrestar){
        return route(PUT("/prestar/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCasePrestar.apply(request.pathVariable("id")), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> giveBack(UseCaseDevolver useCaseDevolver){
        return route(PUT("/devolver/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCaseDevolver.apply(request.pathVariable("id")), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recommendByTipo(UseCaseRecomendarPorTipo useCaseRecomendarPorTipo){
        return route(GET("/recomendar/tipo/{tipo}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCaseRecomendarPorTipo.apply(request.pathVariable("tipo")), DtoRecurso.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recommendByTematica(UseCaseRecomendarPorTematica useCaseRecomendarPorTematica ){
        return route(GET("/recomendar/tematica/{tematica}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCaseRecomendarPorTematica.apply(request.pathVariable("tematica")), DtoRecurso.class))
        );
    }
}
