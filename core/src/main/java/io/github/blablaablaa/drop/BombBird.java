package io.github.blablaablaa.drop;

import com.badlogic.gdx.physics.box2d.World;

public class BombBird extends Bird {
    public BombBird(World world, float x, float y) {
        super(world, x, y, "bomb_bird.png", 3, 0.04f);
    }

    @Override
    public void launch(float angle, float power) {
        System.out.println("Bomb Bird launched!");
        super.launch(angle, power);
    }
}
