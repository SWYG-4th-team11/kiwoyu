package com.swyp.kiwoyu.global.util;

import com.swyp.kiwoyu.global.util.dto.ExpLevelDto;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;

public class ExpProcess {
    public static ExpLevelDto updateExp(Mandalart m, String type){
        Integer expDiff = 0;
        Integer levelDiff = 0;
        Integer newExp = 0;
        if("routine".contentEquals(type)){
            expDiff = 10;
        } else if("small".contentEquals(type)){
            expDiff = 20;
        } else if("middle".contentEquals(type)){
            expDiff = 50;
        } else if("main".contentEquals(type)){
            expDiff = 100;
        }
        if(m.getExp() + expDiff >= 100){
            levelDiff = 1;
//            expDiff = m.getExp() - 100;
            newExp = m.getExp() + expDiff - 100;
        } else {
            newExp = m.getExp() + expDiff;
        }
        return new ExpLevelDto(newExp, m.getLevel()+levelDiff, levelDiff == 1);
    }
}
