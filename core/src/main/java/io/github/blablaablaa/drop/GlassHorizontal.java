package io.github.blablaablaa.drop;

import com.badlogic.gdx.physics.box2d.World;

class GlassHorizontal extends Material {
    public GlassHorizontal(World world, float x, float y, float rotation) {
        super(world, "Glass", 2, "glass_horizontal.png", x, y, 0.38f, 0);
    }
}


