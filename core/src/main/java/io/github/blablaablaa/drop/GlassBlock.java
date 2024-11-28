package io.github.blablaablaa.drop;

import com.badlogic.gdx.physics.box2d.World;

class GlassBlock extends Material {
    public GlassBlock(World world, float x, float y, float rotation) {
        super(world, "Glass", 2, "glass_block.png", x, y, 0.38f, rotation);
    }
}


