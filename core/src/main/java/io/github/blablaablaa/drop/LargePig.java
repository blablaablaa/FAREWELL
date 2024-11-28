package io.github.blablaablaa.drop;


import com.badlogic.gdx.physics.box2d.World;
class LargePig extends Pig {
    public LargePig(World world, float x, float y) {
        super(world, "Large", 3, "small_pig.png", x, y, 0.09f);
    }
}
