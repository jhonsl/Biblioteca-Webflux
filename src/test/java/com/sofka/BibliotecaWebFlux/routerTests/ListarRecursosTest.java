package com.sofka.BibliotecaWebFlux.routerTests;

import com.sofka.BibliotecaWebFlux.collection.Recurso;
import com.sofka.BibliotecaWebFlux.dto.DtoRecurso;
import com.sofka.BibliotecaWebFlux.mapper.MapperUtils;
import com.sofka.BibliotecaWebFlux.repository.RepositoryRecurso;
import com.sofka.BibliotecaWebFlux.router.RecursoRouter;
import com.sofka.BibliotecaWebFlux.useCases.UseCaseListar;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
@ContextConfiguration(classes = {RecursoRouter.class, UseCaseListar.class, MapperUtils.class})
public class ListarRecursosTest {

    @MockBean
    private RepositoryRecurso repositoryRecurso;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetRecursos(){
        Recurso recurso1 = new Recurso();
        recurso1.setAutor("pepe");
        recurso1.setTematica("Drama");
        recurso1.setTitulo("La casa de papel");
        recurso1.setTipo("Serie");
        recurso1.setDisponible(true);

        Recurso recurso2 = new Recurso();
        recurso2.setAutor("juan");
        recurso2.setTematica("Accion");
        recurso2.setTitulo("Dragon ball z");
        recurso2.setTipo("anime");
        recurso2.setDisponible(true);

        when(repositoryRecurso.findAll()).thenReturn(Flux.just(recurso1, recurso2));

        webTestClient.get()
                .uri("/recursos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DtoRecurso.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.get(0).getTitulo()).isEqualTo(recurso1.getTitulo());
                            Assertions.assertThat(userResponse.get(1).getTitulo()).isEqualTo(recurso2.getTitulo());
                            Assertions.assertThat(userResponse.size()).isEqualTo(2);
                        }
                );
    }
}
