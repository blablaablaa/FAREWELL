package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Catapult {
    private Texture texture;
    protected float xPosition;
    protected float yPosition;
    protected float width;
    protected float height;
    public Bird currentBird, launchedBird;
    public boolean isDragging = false;

    private Vector2 initialBirdPosition;

    public Catapult(String imagePath, float x, float y, float scale) {
        this.texture = new Texture(imagePath);
        this.xPosition = x;
        this.yPosition = y;
        this.width = texture.getWidth() * scale;
        this.height = texture.getHeight() * scale;
    }

    public void placeBird(Bird bird) {
        if (bird != null && initialBirdPosition != null) {
            bird.sprite.setPosition(initialBirdPosition.x, initialBirdPosition.y);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, xPosition, yPosition, width, height);
        if (currentBird != null) {
            if (isDragging) {
                currentBird.render(batch);
            } else {
                placeBird(currentBird);
                currentBird.render(batch);
            }
        }
        if (launchedBird != null) {
            launchedBird.updateSpritePosition();
            launchedBird.render(batch);
        }
    }

    public void holdBird(Bird bird) {
        this.currentBird = bird;
        initialBirdPosition = new Vector2(xPosition + width / 2 - 20, yPosition + height / 2 + 10);
        bird.setPosition(initialBirdPosition.x, initialBirdPosition.y);
    }

    public Bird getLaunchedBird() {
        return launchedBird;
    }
    public Bird getCurrentBird() {
        return currentBird;
    }
    public float getLaunchedBirdDistance() {
        return launchedBird.getPosition().dst(xPosition + width / 2, yPosition + height / 2);
    }

    public void launchCurrentBird(float angle, float power, PhysicsWorld world) {
        if (currentBird != null) {
            if(launchedBird != null){
                launchedBird.removeBody(world);
            }
            currentBird.launch(angle, power);
            launchedBird = currentBird;
            currentBird = null;
        }
    }

    public void startDrag(float mouseX, float mouseY) {
        if (currentBird != null) {
            isDragging = true;
            updateBirdPosition(mouseX, mouseY);
        }
    }

    public void updateDrag(float mouseX, float mouseY) {
        if (isDragging) {
            updateBirdPosition(mouseX, mouseY);
        }
    }

    public void stopDrag(float mouseX, float mouseY, PhysicsWorld world) {
        if (isDragging) {
            isDragging = false;
            float angle = calculateAngle(mouseX, mouseY);
            float power = calculatePower(mouseX, mouseY);
            launchCurrentBird(angle, power, world);
        }
    }

    private void updateBirdPosition(float mouseX, float mouseY) {
        if (currentBird != null) {
            float maxDragDistance = 100;
            Vector2 initialPosition = new Vector2(initialBirdPosition.x, initialBirdPosition.y);
            Vector2 dragPosition = new Vector2(mouseX, mouseY);

            float distance = dragPosition.dst(initialPosition);

            if (distance > maxDragDistance) {
                dragPosition = initialPosition.cpy().add(dragPosition.sub(initialPosition).nor().scl(maxDragDistance));
            }

            currentBird.sprite.setCenter(dragPosition.x, dragPosition.y);
        }
    }

    private float calculateAngle(float mouseX, float mouseY) {
        float dx = initialBirdPosition.x - mouseX;
        float dy = initialBirdPosition.y - mouseY;
        return (float) Math.toDegrees(Math.atan2(dy, dx));
    }

    private float calculatePower(float mouseX, float mouseY) {
        float dragDistance = new Vector2(initialBirdPosition.x, initialBirdPosition.y)
            .dst(mouseX, mouseY);
        float maxDragDistance = 100;
        return Math.min(dragDistance, maxDragDistance);
    }

    public void dispose() {
        texture.dispose();
    }
}
