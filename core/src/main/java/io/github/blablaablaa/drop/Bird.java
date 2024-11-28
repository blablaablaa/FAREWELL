package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird {
    protected Sprite sprite;
    protected Body body;
    protected int damage;

    public Bird(World world, float x, float y, String imagePath, int damage, float scale) {
        this.sprite = new Sprite(new Texture(imagePath));
        this.damage = damage;
        this.sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        createPhysicsBody(world, x, y);
        body.setUserData(this);
    }

    public void setPosition(float x, float y) {
        this.sprite.setPosition(x, y);
        this.body.setTransform(x, y, this.body.getAngle());
    }

    private void createPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.3f;
        fixtureDef.friction = 0.5f;
        body.setLinearDamping(0.1f);

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public int getDamage() {
        return damage;
    }

    public void launch(float angle, float power) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("Bird_launch.mp3"));
        sound.play(0.7f, 1.1f, -0.6f);

        Vector2 launchVelocity = new Vector2(power*3.5f, 0).setAngleDeg(angle);
        body.setLinearVelocity(launchVelocity);
        System.out.println("LAUNCHED with velocity: " + launchVelocity);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }


    public void updateSpritePosition() {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
            body.getPosition().y - sprite.getHeight() / 2);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
    public Body getBody() {
        return body;
    }

    public void removeBody(PhysicsWorld world) {
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }


}
