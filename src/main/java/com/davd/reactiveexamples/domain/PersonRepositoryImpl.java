package com.davd.reactiveexamples.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    private final Person michael = new Person(1, "Michael", "Angelo");
    private final Person fiona = new Person(2, "Fiona", "Shrek");
    private final Person sam = new Person(3, "Sam", "Jackson");
    private final Person jesse = new Person(4, "Jesse", "James");
    private final Person jesse2 = new Person(4, "Jesse2", "James2");

    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(michael);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, fiona, sam, jesse, jesse2);
    }
}
