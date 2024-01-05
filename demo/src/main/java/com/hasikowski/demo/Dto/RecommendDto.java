package com.hasikowski.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendDto {

    private List<String> genres;


    private int limit = 1000;
}
