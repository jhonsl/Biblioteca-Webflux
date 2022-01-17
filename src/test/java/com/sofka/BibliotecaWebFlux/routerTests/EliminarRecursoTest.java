package com.sofka.BibliotecaWebFlux.routerTests;

import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import com.sofka.BibliotecaWebFlux.router.RecursoRouter;
import com.sofka.BibliotecaWebFlux.useCases.UseCaseEliminar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
@ContextConfiguration(classes = {RecursoRouter.class, UseCaseEliminar.class, MapperUtils.class})
public class EliminarRecursoTest {

    @MockBean
    private RepositoryRecurso repositoryRecurso;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void deleteRecurso(){
        when(repositoryRecurso.deleteById("8")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/eliminar/{id}", "8")
                .exchange()
                .expectStatus().isAccepted()
                .expectBody().isEmpty();

        Mockito.verify(repositoryRecurso).deleteById("8");
    }
}
