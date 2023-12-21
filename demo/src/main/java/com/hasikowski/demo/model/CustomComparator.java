package com.hasikowski.demo.model;

import java.util.Comparator;

public class CustomComparator implements Comparator<GameRecommendDto> {

    @Override
    public int compare(GameRecommendDto o1, GameRecommendDto o2) {
        return (int) (o1.getCompatibility() *1000 - o2.getCompatibility() * 1000);
    }
}
