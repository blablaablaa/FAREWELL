package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        if (userDataA instanceof Bird && userDataB instanceof Target) {
            handleBirdHit((Bird) userDataA, (Target) userDataB);
        } else if (userDataB instanceof Bird && userDataA instanceof Target) {
            handleBirdHit((Bird) userDataB, (Target) userDataA);
        }
    }

    @Override
    public void endContact(Contact contact) {}

    private void handleBirdHit(Bird bird, Target target) {
        int birdDamage = bird.getDamage();
        target.takeDamage(birdDamage);

        Vector2 velocity = bird.getBody().getLinearVelocity();


        Body targetBody = null;
        if (target instanceof Pig) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("Pig_hit.mp3"));
            sound.play(0.8f, 1.4f, 0.4f);
            targetBody = ((Pig) target).getBody();
        } else if (target instanceof Material) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("Material_hit.mp3"));
            sound.play(0.7f, 1.5f, 0.4f);

            targetBody = ((Material) target).getBody();
        }

        if (targetBody != null) {
            Body birdBody = bird.getBody();
            float impactX = birdBody.getLinearVelocity().x * 0.3f;
            float impactY = birdBody.getLinearVelocity().y * 0.3f;
            targetBody.applyLinearImpulse(impactX, impactY, targetBody.getWorldCenter().x, targetBody.getWorldCenter().y, true);
            System.out.println("HAHA");
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}
