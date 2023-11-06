package com.pblgllgs.webflux.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Path {
    private String base;
    private String version;
    private String entity;

    @Override
    public String toString(){
        return "/"+base+"/"+version+"/"+entity;
    }
}
