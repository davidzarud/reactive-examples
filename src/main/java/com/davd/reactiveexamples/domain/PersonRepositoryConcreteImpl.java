package com.davd.reactiveexamples.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryConcreteImpl implements PersonRepository {

    Person michael = new Person(1, "Michael", "Angelo");
    Person fiona = new Person(2, "Fiona", "Shrek");
    Person sam = new Person(3, "Sam", "Jackson");
    Person jesse = new Person(4, "Jesse", "James");

    @Override
    public Mono<Person> getById(Integer id) {
        return findAll().filter(person -> person.getId().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, fiona, sam, jesse);
    }
}
