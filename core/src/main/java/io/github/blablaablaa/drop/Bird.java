package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Bird {
    protected String type;
    protected float speed;
    protected int damage;
    protected Sprite sprite;

    public Bird(String type, float speed, int damage, String imagePath, float x, float y, float scale) {
        this.type = type;
        this.speed = speed;
        this.damage = damage;
        this.sprite = new Sprite(new Texture(imagePath));
        this.sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        this.sprite.setPosition(x, y);
    }

    public String getType() {
        return type;
    }

    public float getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public abstract void launch();

    public void move(float deltaX, float deltaY) {
        sprite.translate(deltaX, deltaY);
    }

    public void dealDamage(Target target) {
        target.takeDamage(damage);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
