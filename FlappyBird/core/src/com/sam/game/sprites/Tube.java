package com.sam.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by sam on 3/11/17.
 */

public class Tube {
    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    public static final int TUBE_WIDTH = 52;
    private Texture bottomTube, topTube;
    private Vector2 posBoTube, posTopTube;
    private Rectangle boundsTop, boundsBot;
    Random rand;

    public Tube(float x){
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBoTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsBot = new Rectangle(posBoTube.x, posBoTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Vector2 getPosBoTube() {
        return posBoTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBoTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsBot.setPosition(x, posBoTube.y);
        boundsTop.setPosition(x, posTopTube.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsBot) || player.overlaps(boundsTop);
    }

    public void dispose(){
        bottomTube.dispose();
        topTube.dispose();
    }
}
