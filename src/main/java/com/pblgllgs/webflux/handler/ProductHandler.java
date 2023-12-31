package com.pblgllgs.webflux.handler;

import com.pblgllgs.webflux.dto.ProductDto;
import com.pblgllgs.webflux.models.Product;
import com.pblgllgs.webflux.services.ProductService;
import com.pblgllgs.webflux.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<Product> products = productService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        Mono<Product> product = productService.getById(Integer.parseInt(request.pathVariable("id")));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(product, Product.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ProductDto> productDto = request.bodyToMono(ProductDto.class).doOnNext(objectValidator::validate);
        return productDto.flatMap(p ->
                ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productService.save(p), Product.class)
        );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<ProductDto> productDto = request.bodyToMono(ProductDto.class).doOnNext(objectValidator::validate);
        int id = Integer.parseInt(request.pathVariable("id"));
        return productDto.flatMap(p ->
                ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productService.update(id, p), Product.class)
        );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.delete(id), Product.class);
    }
}
