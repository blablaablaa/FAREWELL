package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Target {
    void takeDamage(int damage);
    boolean isDestroyed();
    void render(SpriteBatch batch);
    void dispose();
}
