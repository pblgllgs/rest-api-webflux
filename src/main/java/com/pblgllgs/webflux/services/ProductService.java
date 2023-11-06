package com.pblgllgs.webflux.services;

import com.pblgllgs.webflux.dto.ProductDto;
import com.pblgllgs.webflux.exceptions.CustomException;
import com.pblgllgs.webflux.models.Product;
import com.pblgllgs.webflux.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> getAll() {
        return productRepository.findAll();
    }

    public Mono<Product> getById(int id) {
        return productRepository
                .findById(id)
                .switchIfEmpty(
                        Mono.error(
                                new CustomException("User not found!", HttpStatus.NOT_FOUND)
                        )
                );
    }

    public Mono<Product> save(ProductDto productDto) {
        Mono<Boolean> existName = productRepository.findByName(productDto.name()).hasElement();
        return existName
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ?
                        Mono.error(
                                new CustomException(
                                        "Product with name: " + productDto.name() + " already exists"
                                        , HttpStatus.BAD_REQUEST
                                )
                        )
                        :
                        productRepository.save(Product.builder().name(productDto.name()).price(productDto.price()).build())
                );
    }

    public Mono<Product> update(int id, ProductDto productDto) {
        Mono<Boolean> existsId = productRepository.findById(id).hasElement();
        Mono<Boolean> isRepeatedName = productRepository.repeatedName(id, productDto.name()).hasElement();
        return existsId.flatMap(exists -> Boolean.TRUE.equals(exists) ?
                isRepeatedName.flatMap(isRepeated -> {
                    if (Boolean.TRUE.equals(isRepeated)) {
                        return Mono.error(
                                new CustomException(
                                        "Product name: " + productDto.name() + " already in use",
                                        HttpStatus.BAD_REQUEST
                                )
                        );
                    }
                    return productRepository.save(new Product(id, productDto.name(), productDto.price()));
                })
                :
                Mono.error(
                        new CustomException(
                                "Product with id: " + id + " dont exists",
                                HttpStatus.NOT_FOUND
                        )
                )
        );
    }

    public Mono<Void> delete(int id) {
        Mono<Boolean> existsId = productRepository.findById(id).hasElement();
        return existsId
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ?
                        productRepository.deleteById(id)
                        :
                        Mono.error(
                                new CustomException(
                                        "Product with id: " + id + " dont exists",
                                        HttpStatus.NOT_FOUND
                                )
                        )
                );
    }

}
