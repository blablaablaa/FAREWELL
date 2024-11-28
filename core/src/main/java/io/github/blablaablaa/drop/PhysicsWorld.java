package io.github.blablaablaa.drop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicsWorld {
    private World world;

    public PhysicsWorld() {
        world = new World(new Vector2(0, -75f), true);

        world.setContactListener(new GameContactListener());
    }

    public void step(float deltaTime) {
        world.step(deltaTime, 6, 2);
    }

    public World getWorld() {
        return world;
    }

    public void destroyBody(Body body) {
        if (body != null) {
            world.destroyBody(body);
        }
    }


    public void dispose() {
        world.dispose();
    }
}
