package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;

public class SerializableComponent implements Component {
    public String name;
    public ArrayList<String> data;

    public SerializableComponent(String name){
        this.name = name;
        this.data = new ArrayList<String>();
    }
}
