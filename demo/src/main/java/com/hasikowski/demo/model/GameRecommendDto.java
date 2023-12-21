package com.hasikowski.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@AllArgsConstructor
public class GameRecommendDto {

    private Long ID;

    private String name;

    private  double compatibility;

}

