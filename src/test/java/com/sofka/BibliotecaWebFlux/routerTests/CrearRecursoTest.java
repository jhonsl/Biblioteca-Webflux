package com.sofka.BibliotecaWebFlux.routerTests;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import com.sofka.BibliotecaWebFlux.router.RecursoRouter;
import com.sofka.BibliotecaWebFlux.useCases.UseCaseCrear;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RecursoRouter.class, UseCaseCrear.class, MapperUtils.class})
public class CrearRecursoTest {

    @MockBean
    private RepositoryRecurso repositoryRecurso;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testCreateRecurso(){
        Recurso recurso = new Recurso();
        recurso.setId("xxxxxxx");
        recurso.setAutor("pepe");
        recurso.setTematica("Drama");
        recurso.setTitulo("La casa de papel");
        recurso.setTipo("Serie");
        recurso.setDisponible(true);
        DtoRecurso dtoRecurso = new DtoRecurso(recurso.getId(), recurso.getAutor(), recurso.getTematica(), recurso.getTitulo(), recurso.getTipo(), recurso.isDisponible(), recurso.getFechaPrestamo());
        Mono<Recurso> recursoMono = Mono.just(recurso);
        when(repositoryRecurso.save(any())).thenReturn(recursoMono);

        webTestClient.post()
                .uri("/añadir")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dtoRecurso), DtoRecurso.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                    Assertions.assertEquals(userResponse, recurso.getId());
                        }
                );
    }
}
