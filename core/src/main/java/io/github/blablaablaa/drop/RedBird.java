package io.github.blablaablaa.drop;

import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {
    public RedBird(World world, float x, float y) {
        super(world, x, y, "red_bird.png", 2, 0.075f);
    }

    @Override
    public void launch(float angle, float power) {
        System.out.println("Red Bird launched!");
        super.launch(angle, power);
    }
}
