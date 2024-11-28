package io.github.blablaablaa.drop;

import com.badlogic.gdx.physics.box2d.World;

public class BlueBird extends Bird {
    public BlueBird(World world, float x, float y) {
        super(world, x, y, "blue_bird.png",1 , 0.04f);
    }

    @Override
    public void launch(float angle, float power) {
        System.out.println("Blue Bird launched!");
        super.launch(angle, power);
    }
}
