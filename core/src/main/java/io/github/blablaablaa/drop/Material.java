package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Material implements Target {
    private String materialType;
    public int health;
    private Sprite sprite;
    private boolean destroyed;
    private Body body;

    public Material(World world, String materialType, int health, String imagePath, float x, float y, float scale, float rotation) {
        this.materialType = materialType;
        this.health = health;
        this.sprite = new Sprite(new Texture(imagePath));
        this.sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() *scale);
        this.sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.sprite.setRotation(rotation);
        this.destroyed = false;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }

    public void removeBody(PhysicsWorld world) {
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
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
    public void render(SpriteBatch batch, PhysicsWorld world, LevelScreen screen) {
        if (!destroyed) {
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                body.getPosition().y - sprite.getHeight() / 2);
            sprite.setRotation(body.getAngle() * (180 / (float) Math.PI));
            sprite.draw(batch);
        }
        else{
            if (body != null) {
                world.destroyBody(body);
                body = null;
            }
        }
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public Body getBody() {
        return body;
    }


    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
