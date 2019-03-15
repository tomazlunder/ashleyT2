package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;

public class NameComponent implements Component {
    String name;

    public NameComponent(String name){
        this.name = name;
    }
}
