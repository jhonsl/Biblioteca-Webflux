package com.sofka.BibliotecaWebFlux.routerTests;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import com.sofka.BibliotecaWebFlux.router.RecursoRouter;
import com.sofka.BibliotecaWebFlux.useCases.UseCaseRecomendarPorTipo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RecursoRouter.class, UseCaseRecomendarPorTipo.class, MapperUtils.class})
public class RecomendarPorTipoRecursoTest {

    @MockBean
    private RepositoryRecurso repositoryRecurso;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void recomendarPorTipoRecurso(){
        Recurso recurso1 = new Recurso();
        recurso1.setId("01");
        recurso1.setAutor("pepe");
        recurso1.setTematica("Accion");
        recurso1.setTitulo("La casa de papel");
        recurso1.setTipo("serie");
        recurso1.setDisponible(true);

        Recurso recurso2 = new Recurso();
        recurso2.setId("02");
        recurso2.setAutor("juan");
        recurso2.setTematica("Drama");
        recurso2.setTitulo("Dragon ball z");
        recurso2.setTipo("serie");
        recurso2.setDisponible(true);

        when(repositoryRecurso.findByTipo(recurso1.getTipo())).thenReturn(Flux.just(recurso1, recurso2));

        webTestClient.get()
                .uri("/recomendar/tipo/{tipo}","serie")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DtoRecurso.class)
                .value(userResponse -> {
                    Assertions.assertEquals(userResponse.get(0).getTipo(), recurso1.getTipo());
                    Assertions.assertEquals(userResponse.get(1).getTipo(), recurso2.getTipo());
                });
    }
}
