package com.pblgllgs.webflux.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import org.springframework.data.relational.core.mapping.Table;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table
//@ToString
//public class ProductDto {
//    private String name;
//    private float price;
//}


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record ProductDto(
        @NotBlank(message = "name is mandatory")
        String name,
        @Min(value = 1, message = "price must be greater then 1")
        @Max(value = 10000000, message = "price must be lower then 10000000")
        float price
) {
}