package io.github.blablaablaa.drop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.io.*;
import java.util.ArrayList;


public abstract class LevelScreen implements Screen {
    protected COLE game;
    protected SpriteBatch batch;
    protected Texture background;
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected Stage stage;
    public int pigCount, birdCount;
    protected static final float WORLD_WIDTH = 800;
    protected static final float WORLD_HEIGHT = 480;
    PhysicsWorld physicsWorld;
    String SAVE_FILE;
    public ArrayList<Bird> birds;
    public ArrayList<Pig> pigs;
    public ArrayList<Material> materials;
    Catapult catapult;
    int birdIndex = 0;
    boolean isPaused = false;

    float LEFT_BOUND, RIGHT_BOUND, BOTTOM_BOUND, TOP_BOUND;

    public LevelScreen(COLE game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public Bird createBird(String type, float x, float y) {
        switch (type) {
            case "RedBird":
                return new RedBird(physicsWorld.getWorld(), x, y);
            case "BlueBird":
                return new BlueBird(physicsWorld.getWorld(), x, y);
            case "BombBird":
                return new BombBird(physicsWorld.getWorld(), x, y);
            default:
                throw new IllegalArgumentException("Unknown bird type: " + type);
        }
    }

    public Pig createPig(String type, float x, float y, int health) {
        switch (type) {
            case "SmallPig":
                SmallPig pig = new SmallPig(physicsWorld.getWorld(), x, y);
                pig.setHealth(health);
                return pig;
            case "MediumPig":
                MediumPig pig2 = new MediumPig(physicsWorld.getWorld(), x, y);
                pig2.setHealth(health);
                return pig2;
            case "LargePig":
                LargePig pig3 = new LargePig(physicsWorld.getWorld(), x, y);
                pig3.setHealth(health);
                return pig3;
            default:
                throw new IllegalArgumentException("Unknown pig type: " + type);
        }
    }

    public Material createMaterial(String type, float x, float y, int health) {
        switch (type) {
            case "WoodBlock":
                WoodBlock block = new WoodBlock(physicsWorld.getWorld(), x, y, 0);
                block.setHealth(health);
                return block;
            case "GlassBlock":
                GlassBlock block2 = new GlassBlock(physicsWorld.getWorld(), x, y, 0);
                block2.setHealth(health);
                return block2;
            case "SteelBlock":
                SteelBlock block3 = new SteelBlock(physicsWorld.getWorld(), x, y, 0);
                block3.setHealth(health);
                return block3;
            case "GlassHorizontal":
                GlassHorizontal horizontal = new GlassHorizontal(physicsWorld.getWorld(), x, y, 0);
                horizontal.setHealth(health);
                return horizontal;
            default:
                throw new IllegalArgumentException("Unknown material type: " + type);
        }
    }

    public void saveLevelState() {
        LevelState state = new LevelState(birdIndex, isPaused, pigCount, birdCount);

        for (Bird bird : birds) {
            if (bird.getBody() != null) {
                Vector2 position = bird.getBody().getPosition();
                Vector2 velocity = bird.getBody().getLinearVelocity();
                float angularVelocity = bird.getBody().getAngularVelocity();
                state.birds.add(new LevelState.BirdState(
                    position.x, position.y, velocity.x, velocity.y, angularVelocity, bird.getClass().getSimpleName()
                ));
            }
            else{
                state.birdIndex--;
            }
        }

        for (Pig pig : pigs) {
            if (pig != null && pig.getBody() != null) {
                Vector2 position = pig.getBody().getPosition();
                Vector2 velocity = pig.getBody().getLinearVelocity();
                float angularVelocity = pig.getBody().getAngularVelocity();
                state.pigs.add(new LevelState.PigState(
                    position.x, position.y, velocity.x, velocity.y, angularVelocity, pig.health, pig.getClass().getSimpleName()
                ));
            }
        }

        for (Material block : materials) {
            if (block != null && block.getBody() != null) {
                Vector2 position = block.getBody().getPosition();
                Vector2 velocity = block.getBody().getLinearVelocity();
                float angularVelocity = block.getBody().getAngularVelocity();
                state.materials.add(new LevelState.MaterialState(
                    position.x, position.y, velocity.x, velocity.y, angularVelocity, block.health, block.getClass().getSimpleName() // Save health
                ));
            }
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(state);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(SAVE_FILE);
        System.out.println("File size: " + file.length());

    }

    public void loadLevelState() {
        File file = new File(SAVE_FILE);
        System.out.println("File size: " + file.length());

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            LevelState state = (LevelState) in.readObject();
            catapult.currentBird = null;
            catapult.launchedBird = null;
            birdIndex = state.birdIndex;
            isPaused = state.isPaused;
            pigCount = state.pigCount;
            birdCount = state.birdCount;

            for(var bird: birds){
                bird.removeBody(physicsWorld);
            }
            for (var material: materials){
                material.health = 0;
                material.removeBody(physicsWorld);
            }
            for(var pig: pigs){
                pig.health = 0;
                pig.removeBody(physicsWorld);
            }

            birds.clear();
            pigs.clear();
            materials.clear();

            for (LevelState.BirdState birdState : state.birds) {
                Bird bird = createBird(birdState.type, birdState.x, birdState.y);
                bird.getBody().setLinearVelocity(birdState.velocityX, birdState.velocityY);
                bird.getBody().setAngularVelocity(birdState.angularVelocity);
                birds.add(bird);
            }

            for (LevelState.PigState pigState : state.pigs) {
                Pig pig = createPig(pigState.type, pigState.x, pigState.y, pigState.health);
                pig.getBody().setLinearVelocity(pigState.velocityX, pigState.velocityY);
                pig.getBody().setAngularVelocity(pigState.angularVelocity);
                pigs.add(pig);
            }

            for (LevelState.MaterialState materialState : state.materials) {
                Material material = createMaterial(materialState.type, materialState.x, materialState.y, materialState.health);
                material.getBody().setLinearVelocity(materialState.velocityX, materialState.velocityY);
                material.getBody().setAngularVelocity(materialState.angularVelocity);
                materials.add(material);
            }
            if (!birds.isEmpty() && birdIndex < birds.size()) {
                catapult.holdBird(birds.get(birdIndex));
                System.out.println("DONT ENTER");
            }
            if (!birds.isEmpty() && birdIndex > 0 ) {
                catapult.launchedBird = birds.get(birdIndex-1);
                System.out.println("ENTER");
            }
            System.out.println("Game state loaded successfully!");

            System.out.println(birdIndex+ " " + birds.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void launchBird() {
        if (catapult != null && birdIndex < birds.size()) {
            birdIndex++;
            if (birdIndex < birds.size()) {
                catapult.holdBird(birds.get(birdIndex));
            } else {
                catapult.currentBird = null;
            }
        }
    }

    public void createGround() {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(WORLD_WIDTH / 2, 130);
        Body groundBody = physicsWorld.getWorld().createBody(groundBodyDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(WORLD_WIDTH / 2, 1);
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.density = 0;
        groundFixtureDef.friction = 0.99f;
        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();
    }

    public void checkBoundsAndReboundBirds(ArrayList<Bird> birds) {
        for (Bird bird : birds) {
            Body body = bird.getBody();
            if(body == null) continue;
            Vector2 position = body.getPosition();
            if (position.x < LEFT_BOUND) {
                body.setTransform(LEFT_BOUND, position.y, body.getAngle());
                body.setLinearVelocity(15f, body.getLinearVelocity().y);
            } else if (position.x > RIGHT_BOUND) {
                body.setTransform(RIGHT_BOUND, position.y, body.getAngle());
                body.setLinearVelocity(-20f, body.getLinearVelocity().y);
            }
            if (position.y < BOTTOM_BOUND) {
                body.setTransform(position.x, BOTTOM_BOUND, body.getAngle());
                body.setLinearVelocity(body.getLinearVelocity().x, 15f);
            } else if (position.y > TOP_BOUND) {
                body.setTransform(position.x, TOP_BOUND, body.getAngle());
                body.setLinearVelocity(body.getLinearVelocity().x, -20f);
            }
        }
    }

    public void checkBoundsAndReboundPigs(ArrayList<Pig> targets) {
        for (Target target : targets) {
            if (target == null) continue;
            Body body = target.getBody();
            if (body == null) continue;
            Vector2 position = body.getPosition();
            if (position.x < LEFT_BOUND) {
                body.setTransform(LEFT_BOUND, position.y, body.getAngle());
            } else if (position.x > RIGHT_BOUND) {
                body.setTransform(RIGHT_BOUND, position.y, body.getAngle());
            }
            if (position.y < BOTTOM_BOUND) {
                body.setTransform(position.x, BOTTOM_BOUND, body.getAngle());
            } else if (position.y > TOP_BOUND) {
                body.setTransform(position.x, TOP_BOUND, body.getAngle());
            }
        }
    }

    public void checkBoundsAndRebound(ArrayList<Material> targets) {
        for (Target target : targets) {
            if (target == null) continue;
            Body body = target.getBody();
            if (body == null) continue;
            Vector2 position = body.getPosition();
            if (position.x < LEFT_BOUND) {
                body.setTransform(LEFT_BOUND, position.y, body.getAngle());
            } else if (position.x > RIGHT_BOUND) {
                body.setTransform(RIGHT_BOUND, position.y, body.getAngle());
            }
            if (position.y < BOTTOM_BOUND) {
                body.setTransform(position.x, BOTTOM_BOUND, body.getAngle());
            } else if (position.y > TOP_BOUND) {
                body.setTransform(position.x, TOP_BOUND, body.getAngle());
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT); // Draw background
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public void killPig(){
        pigCount--;
        System.out.println("Killed a pig");
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
    }

    protected abstract void createLevel();
}
