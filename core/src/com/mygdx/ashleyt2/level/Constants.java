package com.mygdx.ashleyt2.level;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    //WORLD
    public static Vector2 gravity = new Vector2(0f,-17.8f);

    //PLAYER
    public static float playerRadius = 0.5f;
    public static float playerHorizontalAcc = 0.5f;
    public static float playerHorizontalDec = 1f;
    public static float playerMaxHorizontalVelocity = 8f;
    public static float playerJumpVelocity = 10f;

    public static float playerDensity = 1;
    public static float playerFriction = 0.3f;
    public static float playerRestitution = 0f;

    public static float playerAfterBounceSpeed = 12f;

    public static float playerAirHorizontalDec = 0.03f;
    public static float playerBulletSpeed = 20f;

    public static String playerAtlasRegionKey = "player";

}
