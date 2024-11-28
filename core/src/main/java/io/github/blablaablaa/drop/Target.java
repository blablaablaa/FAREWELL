package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public interface Target {
    void takeDamage(int damage);
    boolean isDestroyed();
    void render(SpriteBatch batch, PhysicsWorld world, LevelScreen screen);
    void dispose();

    Body getBody();
}
