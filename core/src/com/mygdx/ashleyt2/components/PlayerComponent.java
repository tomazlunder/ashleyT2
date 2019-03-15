package com.mygdx.ashleyt2.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    private static int playerIDcounter = 0;

    public int playerID;

    public enum PlayerState{GROUNDED,AIR,BULLET};
    public PlayerState playerState;

    public PlayerComponent(){
        this.playerID = playerIDcounter;
        this.playerIDcounter++;

        this.playerState = PlayerState.AIR;
    };
}
