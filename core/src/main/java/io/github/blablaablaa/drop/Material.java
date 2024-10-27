package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Material implements Target {
    private String materialType;
    private int health;
    private Sprite sprite;
    private boolean destroyed;
    private float rotation;

    public Material(String materialType, int health, String imagePath, float x, float y, float scale, float rotation) {
        this.materialType = materialType;
        this.health = health;
        this.sprite = new Sprite(new Texture(imagePath));
        this.sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        this.rotation = rotation;

        this.sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.sprite.setPosition(x, y);
        this.destroyed = false;
    }

    public String getMaterialType() {
        return materialType;
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
                System.out.println(materialType + " block destroyed!");
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!destroyed) {
            sprite.setRotation(rotation);
            sprite.draw(batch);
            sprite.setRotation(0);
        }
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
