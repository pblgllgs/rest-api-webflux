package com.pblgllgs.webflux.repository;

import com.pblgllgs.webflux.models.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {
    Mono<Product> findByName(String name);

    @Query(value = "SELECT * FROM product WHERE id<> :id and name =:name")
    Mono<Product> repeatedName(int id, String name);
}
