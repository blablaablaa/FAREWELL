package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pig implements Target {
    private String type;
    private int health;
    private Sprite sprite;  // Sprite to manage the pig's texture and position
    private boolean destroyed;

    public Pig(String type, int health, String imagePath, float x, float y, float scale) {
        this.type = type;
        this.health = health;
        this.sprite = new Sprite(new Texture(imagePath));  // Create sprite from texture
        this.sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        this.sprite.setPosition(x, y);  // Set initial position

        this.destroyed = false;  // Pig is initially not destroyed
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void takeDamage(int damage) {
        if (!destroyed) {
            health -= damage;
            if (health <= 0) {
                destroyed = true;
                System.out.println(type + " Pig died!");
                // You could also trigger animations or effects here if desired
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;  // Returns whether the pig is destroyed or not
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!destroyed) {
            sprite.draw(batch);  // Draw the pig sprite if it's not destroyed
        }
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
