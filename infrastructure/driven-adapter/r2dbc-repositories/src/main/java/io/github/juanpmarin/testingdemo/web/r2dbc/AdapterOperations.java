package io.github.juanpmarin.testingdemo.web.r2dbc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;


@RequiredArgsConstructor
public class AdapterOperations<E, D, I, R extends ReactiveCrudRepository<D, I>> {

    protected final R repository;
    protected final Function<E, D> toData;
    protected final Function<D, E> toEntity;

    @Transactional
    public Flux<E> findAll() {
        return repository.findAll()
                .map(this.toEntity);
    }

    @Transactional
    public Mono<E> save(E entity) {
        return Mono.just(entity)
                .map(this.toData)
                .flatMap(repository::save)
                .map(this.toEntity);
    }

}
