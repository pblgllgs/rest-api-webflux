package com.pblgllgs.webflux.controllers;

import com.pblgllgs.webflux.models.Product;
import com.pblgllgs.webflux.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Mono<Product>> saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Flux<Product>> findAll(){
        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Mono<Product>> findProductById(@PathVariable("productId") int id){
        return new ResponseEntity<>(productService.getById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Mono<Void>> deleteProductById(@PathVariable("productId") int id){
        return new ResponseEntity<>(productService.delete(id),HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Mono<Product>> updateProductById(
            @PathVariable("productId") int id,
            @RequestBody Product product
    ){
        return new ResponseEntity<>(productService.update(id,product),HttpStatus.OK);
    }
}
