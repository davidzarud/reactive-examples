package com.davd.reactiveexamples.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = new PersonRepositoryImpl();
    }

    @Test
    void getByIdBlock() {

        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void getByIdSubscribe() {

        Mono<Person> personMono = personRepository.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person);
        });
    }

    @Test
    void getByIdMapFunction() {

        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(person -> {
            return person.getLastName();
        }).subscribe(firstName -> {
            System.out.println(firstName);
        });
    }

    @Test
    void fluxTestBlockFirst() {

        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person);
    }

    @Test
    void testFluxSubscribe() {

        Flux<Person> personFlux = personRepository.findAll();

        StepVerifier.create(personFlux).expectNextCount(5).verifyComplete();

        personFlux.subscribe(person -> {
            System.out.println(person);
        });
    }

    @Test
    void testFluxToListMono() {

        Flux<Person> personFlux = personRepository.findAll();

        personFlux.collectList().subscribe(list -> {
            list.forEach(person -> {
                System.out.println(person);
            });
        });
    }

    @Test
    void testFindPersonById() {

        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 3;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).next();

        personMono.subscribe(person -> System.out.println(person));
    }

    @Test
    void testFindPersonByIdNotFound() {

        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 5;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).next();

        personMono.subscribe(person -> System.out.println(person));
    }

    @Test
    void testFindPersonByIdNotFoundWithException() {

        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 5;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).single();

        personMono.subscribe(person -> System.out.println(person));
    }

    @Test
    void testFindPersonByIdNotFoundWithExceptionDoSomething() {

        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 5;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).single();

        personMono.doOnError(t -> {
            System.out.println("I went BOOM!");
        }).onErrorReturn(Person.builder().id(id).build())
                .subscribe(person -> {
            System.out.println(person);
        });
    }

    @Test
    void testFindPersonByIdSameId() {

        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 4;

        Mono<Person> personMono = personFlux.filter(person -> person.getId().equals(id)).next();

        personMono.subscribe(person -> System.out.println(person));
    }
}