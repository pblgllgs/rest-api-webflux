package com.pblgllgs.webflux.router;

import com.pblgllgs.webflux.handler.ProductHandler;
import com.pblgllgs.webflux.models.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class ProductRouter {

    private static final String PRODUCT_ID= "/{id}";

    @Bean
    RouterFunction<ServerResponse> router(ProductHandler handler){
        Path path = Path.builder()
                .base("api")
                .version("v2")
                .entity("products")
                .build();
        return RouterFunctions.route()
                .GET(path.toString(), handler::getAll)
                .GET(path.toString() + PRODUCT_ID,handler::getOne)
                .POST(path.toString(),handler::save)
                .PUT(path.toString() + PRODUCT_ID,handler::update)
                .DELETE(path.toString()+ PRODUCT_ID,handler::delete)
                .build();
    }

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }
}
