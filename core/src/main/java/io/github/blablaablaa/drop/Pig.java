package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Pig implements Target {
    private String type;
    public int health;
    private Sprite sprite;
    private boolean destroyed;
    private Body body;

    public Pig(World world, String type, int health, String imagePath, float x, float y, float scale) {
        this.type = type;
        this.health = health;
        this.sprite = new Sprite(new Texture(imagePath));
        this.sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        this.sprite.setPosition(x, y);
        this.destroyed = false;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.5f;
        body.setLinearDamping(0.3f);

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void takeDamage(int damage) {
        if (!destroyed) {
            health -= damage;
            System.out.println("I am hit with damage " + damage );
            if (health <= 0) {
                destroyed = true;
                System.out.println(type + " Pig died!");
            }
        }

    }
    public void removeBody(PhysicsWorld world) {
        if (body != null) {
            world.destroyBody(body);
            body = null;
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
            sprite.draw(batch);
        }
        else{
            if (body != null) {
                screen.killPig();
                world.destroyBody(body);
                body = null;
            }
        }
    }

    public Body getBody() {
        return body;
    }


    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
