package com.pblgllgs.webflux.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@ToString
@Builder
public class Product {

    @Id
    private int id;
    private String name;
    private float price;


}
