package com.sam.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sam.game.FlappyDemo;

/**
 * Created by sam on 3/10/17.
 */

public class MenuState extends State{

    private Texture background;
    private Texture playbtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        playbtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        sb.draw(playbtn, FlappyDemo.WIDTH/2 - playbtn.getWidth()/2, FlappyDemo.HEIGHT/2 - playbtn.getHeight()/2);
        sb.end();
    }

    @Override
    protected void dispose() {
        background.dispose();
        playbtn.dispose();
    }
}
