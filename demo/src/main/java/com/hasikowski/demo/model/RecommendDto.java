package com.hasikowski.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendDto {

    private List<String> genres;

    private double compatibility;

    private int limit = 1000;
}
