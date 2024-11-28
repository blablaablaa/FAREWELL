package io.github.blablaablaa.drop;

import com.badlogic.gdx.physics.box2d.World;

class SmallPig extends Pig {
    public SmallPig(World world, float x, float y) {
        super(world, "Small", 1, "small_pig.png", x, y, 0.055f);
    }
}


