package com.sofka.BibliotecaWebFlux.routerTests;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import com.sofka.BibliotecaWebFlux.router.RecursoRouter;
import com.sofka.BibliotecaWebFlux.useCases.UseCasePrestar;
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

import static org.mockito.Mockito.when;

@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RecursoRouter.class, UseCasePrestar.class, MapperUtils.class})
public class PrestarRecursoTest {

    @MockBean
    private RepositoryRecurso repositoryRecurso;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void lendRecurso(){
        Recurso recurso = new Recurso();
        recurso.setId("8");
        recurso.setAutor("pepe");
        recurso.setTematica("Drama");
        recurso.setTitulo("La casa de papel");
        recurso.setTipo("Serie");
        recurso.setDisponible(false);

        when(repositoryRecurso.findById(recurso.getId())).thenReturn(Mono.just(recurso));

        webTestClient.put()
                .uri("/prestar/{id}", "8")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse ->{
                    Assertions.assertEquals("El recurso ya se encuentra prestado desde el: " + recurso.getFechaPrestamo(),userResponse);
                });
    }
}
