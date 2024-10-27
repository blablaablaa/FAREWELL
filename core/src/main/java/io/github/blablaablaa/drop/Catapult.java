package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Catapult {
    private Texture texture;
    protected float xPosition;
    protected float yPosition;
    protected float width;
    protected float height;

    public Catapult(String imagePath, float x, float y, float scale) {
        this.texture = new Texture(imagePath);
        this.xPosition = x;
        this.yPosition = y;
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;

        System.out.println("Catapult Texture Size: " + texture.getWidth() + "x" + texture.getHeight());
        System.out.println("Scaled Catapult Size: " + width + "x" + height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, xPosition, yPosition, width, height);
    }

    public void placeBird(Bird bird) {
        float birdX = xPosition + (width - bird.sprite.getWidth()) / 2;
        float birdY = yPosition + height;
        bird.sprite.setPosition(birdX, birdY);
    }

    public void dispose() {
        texture.dispose();
    }
}
