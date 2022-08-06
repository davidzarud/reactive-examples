package com.davd.reactiveexamples.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryConcreteImplTest {

    private PersonRepositoryConcreteImpl personRepositoryConcrete;

    @BeforeEach
    void setUp() {
        personRepositoryConcrete = new PersonRepositoryConcreteImpl();
    }

    @Test
    void getByIdExpectSam() {

        final Integer id = 3;

        Mono<Person> personMono = personRepositoryConcrete.getById(id);

        StepVerifier.create(personMono)
                .expectNextCount(1)
                .verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person);
            Assertions.assertNotNull(person);
            Assertions.assertEquals(person.getId(), id);
            Assertions.assertEquals(person.getFirstName(), "Sam");
        });
    }

    @Test
    void getByIdExpectFiona() {

        final Integer id = 2;

        Mono<Person> personMono = personRepositoryConcrete.getById(id);

        StepVerifier.create(personMono)
                .expectNextCount(1)
                .verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person);
            Assertions.assertNotNull(person);
            Assertions.assertEquals(person.getId(), id);
            Assertions.assertEquals(person.getFirstName(), "Fiona");
        });
    }

    @Test
    void getByIdExpectEmptyMono() {

        final Integer id = 8;

        Mono<Person> personMono = personRepositoryConcrete.getById(id);

        StepVerifier.create(personMono)
                .verifyComplete();

        personMono.subscribe(person -> {
            Assertions.assertNull(person);
        });
    }
}