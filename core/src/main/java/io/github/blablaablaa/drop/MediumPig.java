package io.github.blablaablaa.drop;


import com.badlogic.gdx.physics.box2d.World;
class MediumPig extends Pig {
    public MediumPig(World world, float x, float y) {
        super(world, "Medium", 2, "small_pig.png", x, y, 0.07f);
    }
}
