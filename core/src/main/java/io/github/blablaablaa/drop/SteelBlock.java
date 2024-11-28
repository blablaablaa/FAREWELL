package io.github.blablaablaa.drop;

import com.badlogic.gdx.physics.box2d.World;

class SteelBlock extends Material {
    public SteelBlock(World world, float x, float y, float rotation) {
        super(world, "Steel", 3, "steel_block.png", x, y, 0.5f, rotation);
    }
}
