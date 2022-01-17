package com.sofka.BibliotecaWebFlux.routerTests;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import com.sofka.BibliotecaWebFlux.router.RecursoRouter;
import com.sofka.BibliotecaWebFlux.useCases.UseCaseDevolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RecursoRouter.class, UseCaseDevolver.class, MapperUtils.class})
public class DevolverRecursoTest {

    @MockBean
    private RepositoryRecurso repositoryRecurso;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void giveBackRecurso() {
        Recurso recurso = new Recurso();
        recurso.setId("8");
        recurso.setAutor("pepe");
        recurso.setTematica("Drama");
        recurso.setTitulo("La casa de papel");
        recurso.setTipo("Serie");
        recurso.setDisponible(true);
        recurso.setFechaPrestamo(LocalDate.now());

        when(repositoryRecurso.findById(recurso.getId())).thenReturn(Mono.just(recurso));

        webTestClient.put()
                .uri("/devolver/{id}", "8")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                    Assertions.assertEquals(userResponse, "El recurso no ha sido prestado");
                });
    }
}
