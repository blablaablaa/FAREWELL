package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;
import java.util.ArrayList;

public class Level1Screen extends LevelScreen {
    public Button pauseButton;
    private Table table;
    private Texture blurredBackground;
    public Stage pauseStage;

    private SmallPig smallPig;
    private MediumPig mediumPig;
    private LargePig largePig;
    private float timeAccumulated = 0f;
    private WoodBlock woodBlock1, woodBlock2, woodBlock3, woodBlock4;

    private LevelSelectionScreen levelSelectionScreen;

    private Music backgroundMusic;
    private Sound buttonClickSound;
    public Level1Screen(COLE game, LevelSelectionScreen screen) {
        super(game);
        batch = new SpriteBatch();
        background = new Texture("level1_background.png");
        blurredBackground = new Texture("level1_background.png");
        pauseStage = new Stage(viewport, batch);
        physicsWorld = new PhysicsWorld();
        this.pigCount = 1;
        this.birdCount = 3;
        this.levelSelectionScreen = screen;
        this.SAVE_FILE = "level1_save_file.txt";
        this.birdIndex = 0;
        this.isPaused = false;
        this.LEFT_BOUND = 0;
        this.BOTTOM_BOUND = 0;
        this.RIGHT_BOUND = WORLD_WIDTH-15;
        this.TOP_BOUND = WORLD_HEIGHT-15;
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("Button_sound.mp3"));
    }

    @Override
    public void show() {
        super.show();
        createLevel();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Level_sound.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.05f);
        backgroundMusic.play();
    }

    @Override
    protected void createLevel() {
        Skin pauseSkin = new Skin(Gdx.files.internal("ui/pause_skin.json"));
        pauseButton = new Button(pauseSkin);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f,2f,0);
                isPaused = true;
            }
        });
        table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.add(pauseButton).pad(10).size(70, 70);
        stage.addActor(table);
        createPauseMenu();
        createGround();
        catapult = new Catapult("catapult.png", 150, 135, 0.01f);
        birds = new ArrayList<>();
        pigs = new ArrayList<>();
        materials = new ArrayList<>();

        birds.add(new RedBird(physicsWorld.getWorld(), 140, 190));
        birds.add(new BlueBird(physicsWorld.getWorld(), 100, 135));
        birds.add(new BombBird(physicsWorld.getWorld(), 55, 135));
        if (!birds.isEmpty()) {
            catapult.holdBird(birds.get(birdIndex));
        }
        smallPig = new SmallPig(physicsWorld.getWorld(), 590, 210);
        woodBlock1 = new WoodBlock(physicsWorld.getWorld(), 540, 132, 0);
        woodBlock2 = new WoodBlock(physicsWorld.getWorld(), 580, 132, 0);
        woodBlock3 = new WoodBlock(physicsWorld.getWorld(), 630, 132, 0);
        woodBlock4 = new WoodBlock(physicsWorld.getWorld(), 590, 170, 90);
        pigs.add(smallPig);
        materials.add(woodBlock1);
        materials.add(woodBlock2);
        materials.add(woodBlock3);
        materials.add(woodBlock4);
    }

    public void createPauseMenu() {
        Skin resumeSkin = new Skin(Gdx.files.internal("ui/resume_skin.json"));
        Skin exitSkin = new Skin(Gdx.files.internal("ui/exit_skin.json"));
        Skin saveSkin = new Skin(Gdx.files.internal("ui/save_skin.json"));
        Skin restoreSkin = new Skin(Gdx.files.internal("ui/restore_skin.json"));
        Button resumeButton = new Button(resumeSkin);
        Button exitButton = new Button(exitSkin);
        Button saveButton = new Button(saveSkin);
        Button restoreButton = new Button(restoreSkin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f,2f,0);
                isPaused = false;
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.7f,2f,0);
                backgroundMusic.stop();
                game.setScreen(levelSelectionScreen);
            }
        });
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f,2f,0);
                saveLevelState();
            }
        });
        restoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f,2f,0);
                loadLevelState();
            }
        });
        Table pauseTable = new Table();
        pauseTable.center();
        pauseTable.setFillParent(true);
        pauseTable.add(resumeButton).size(170, 50).padBottom(20);
        pauseTable.row();
        pauseTable.add(exitButton).size(170, 50).padBottom(20);
        pauseTable.row();
        pauseTable.add(saveButton).size(170, 50).padBottom(20);
        pauseTable.row();
        pauseTable.add(restoreButton).size(170, 50);
        pauseStage.addActor(pauseTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if (isPaused) {
            batch.begin();
            batch.draw(blurredBackground, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            batch.end();
            Gdx.input.setInputProcessor(pauseStage);
            pauseStage.act(delta);
            pauseStage.draw();
        } else {
            physicsWorld.step(delta);
            checkBoundsAndReboundBirds(birds);
            checkBoundsAndReboundPigs(pigs);
            checkBoundsAndRebound(materials);
            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            catapult.render(batch);

//            System.out.println("HI");
            for(var pig: pigs){
                pig.render(batch, physicsWorld, this);
            }
//            System.out.println("HI12");
            for(var material: materials){
                material.render(batch, physicsWorld, this);
            }
            batch.end();
            stage.act(delta);
            stage.draw();
//            System.out.println("HELLO");
            Gdx.input.setInputProcessor(stage);
//            System.out.println(birdCount);
            if (Gdx.input.isTouched() && !isPaused) {
                float mouseX = Gdx.input.getX();
                float mouseY = Gdx.input.getY();
                Vector3 screenCoordinates = new Vector3(mouseX, mouseY, 0);
                Vector3 worldCoordinates = camera.unproject(screenCoordinates);
                mouseX = worldCoordinates.x;
                mouseY = worldCoordinates.y;
                if (catapult.currentBird != null) {
                    Rectangle birdRect = catapult.currentBird.sprite.getBoundingRectangle();
                    if (birdRect.contains(mouseX, mouseY) && !catapult.isDragging) {
                        catapult.startDrag(mouseX, mouseY);
                    } else if (catapult.isDragging) {
                        catapult.updateDrag(mouseX, mouseY);
                    }
                }
            } else if (!Gdx.input.isTouched() && catapult.isDragging) {
                float mouseX = Gdx.input.getX();
                float mouseY = Gdx.input.getY();
                Vector3 screenCoordinates = new Vector3(mouseX, mouseY, 0);
                Vector3 worldCoordinates = camera.unproject(screenCoordinates);
                catapult.stopDrag(worldCoordinates.x, worldCoordinates.y, physicsWorld);
                birdCount--;
            }
            if(birdIndex < birds.size() && catapult.getCurrentBird() == null && catapult.getLaunchedBird() != null){
                float dist = catapult.getLaunchedBirdDistance();
                if(dist > 50) {
                    launchBird();
                }
            }
        }
        if(pigCount <= 0){
            timeAccumulated += delta;
            levelSelectionScreen.completedLevel1();
            backgroundMusic.stop();
            if(timeAccumulated>=2){

                game.setScreen(new VictoryScreen(game, levelSelectionScreen));
            }
        }
        float x = 10000, y = 10000;
        if(catapult.launchedBird != null) {
             x = catapult.launchedBird.getBody().getLinearVelocity().x;
             y = catapult.launchedBird.getBody().getLinearVelocity().y;
        }

        System.out.println(x + " " + y);
        if(birdCount <= 0 && Math.abs(x) <= 10 && Math.abs(y) <= 3){
            System.out.println("Time accumulated : " + timeAccumulated);

            timeAccumulated += delta;
            if(timeAccumulated >= 6){
                backgroundMusic.stop();
                game.setScreen(new DefeatScreen(game, levelSelectionScreen));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
        pauseStage.clear();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        blurredBackground.dispose();
        physicsWorld.dispose();
    }
}
