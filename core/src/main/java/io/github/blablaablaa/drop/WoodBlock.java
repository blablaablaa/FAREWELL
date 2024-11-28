package io.github.blablaablaa.drop;
import com.badlogic.gdx.physics.box2d.World;

class WoodBlock extends Material {
    public WoodBlock(World world, float x, float y, float rotation) {
        super(world, "Wood", 1, "wood_block.png", x, y, 0.8f, rotation);
    }
}


