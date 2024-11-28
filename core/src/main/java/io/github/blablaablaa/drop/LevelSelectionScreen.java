package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.io.ObjectOutputStream;

public class LevelSelectionScreen implements Screen {
    private COLE game;
    private SpriteBatch batch;
    private Texture background;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin, skin2, skin3, saveSkin, exitSkin;
    private Table table;
    private float timeAccumulator = 0f;
    private static final float SWITCH_INTERVAL = 0.5f;
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 480;
    private Button level1Button, level2Button, level3Button, saveButton, exitButton;

    public boolean completedLevel1, completedLevel2;
    private static final String SAVE_FILE = "save_file.txt";
    private Sound buttonClickSound;

    public LevelSelectionScreen(COLE game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        background = new Texture("help2.jpg");
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/L1_skin.json"));
        skin2 = new Skin(Gdx.files.internal("ui/L2_skin.json"));
        skin3 = new Skin(Gdx.files.internal("ui/L3_skin.json"));

        saveSkin = new Skin(Gdx.files.internal("ui/save_skin.json"));
        exitSkin = new Skin(Gdx.files.internal("ui/exit_skin.json"));

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("Button_sound.mp3"));

        this.completedLevel1 = false;
        this.completedLevel2 = false;

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        createLevelButtons();
    }

    public LevelSelectionScreen getScreen() {
        return this;
    }

    private void createLevelButtons() {
        level1Button = new Button(skin);
        level2Button = new Button(skin2);
        level3Button = new Button(skin3);
        saveButton = new Button(saveSkin);
        exitButton = new Button(exitSkin);

        level2Button.setDisabled(true);
        level3Button.setDisabled(true);

        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!level1Button.isDisabled()) {
                    buttonClickSound.play(0.7f,2f,0);
                    TapToPlayScreen.backgroundMusic.stop();
                    game.setScreen(new Level1Screen(game, getScreen()));
                }
            }
        });

        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!level2Button.isDisabled()) {
                    buttonClickSound.play(0.7f,2f,0);
                    TapToPlayScreen.backgroundMusic.stop();
                    game.setScreen(new Level2Screen(game, getScreen()));
                }
            }
        });

        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!level3Button.isDisabled()) {
                    buttonClickSound.play(0.7f,2f,0);
                    TapToPlayScreen.backgroundMusic.stop();
                    game.setScreen(new Level3Screen(game, getScreen()));
                }
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Save button clicked!");

                GameState gameState = new GameState(completedLevel1, completedLevel2);
                buttonClickSound.play(0.7f,2f,0);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(Gdx.files.local(SAVE_FILE).write(false));
                    oos.writeObject(gameState);
                    oos.close();
                    System.out.println("Game state saved successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to save game state.");
                }
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f,2f,0);
                TapToPlayScreen.backgroundMusic.stop();
                game.switchToTapToPlayScreen();
            }
        });

        table.add(level1Button).size(90, 90).padRight(50).center().pad(10);
        table.add(level2Button).size(90, 90).center().pad(150);
        table.add(level3Button).size(90, 90).padLeft(50).center().pad(10);
        table.padTop(10);

        Table saveTable = new Table();
        saveTable.setFillParent(true);
        saveTable.center();
        saveTable.add(saveButton).size(150, 40).padTop(300).padRight(30);
        saveTable.add(exitButton).size(150, 40).padTop(300).padLeft(30);
        stage.addActor(table);
        stage.addActor(saveTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        TapToPlayScreen.backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.end();

        timeAccumulator += delta;
        if (timeAccumulator >= SWITCH_INTERVAL) {
            timeAccumulator = 0f;
            if (!level1Button.isDisabled()) {
                if (level1Button.getStyle().up == skin.getDrawable("L1_up")) {
                    level1Button.getStyle().up = skin.getDrawable("L1_hover");
                } else {
                    level1Button.getStyle().up = skin.getDrawable("L1_up");
                }
            }

            if (!level2Button.isDisabled()) {
                if (level2Button.getStyle().up == skin2.getDrawable("L2_up.9")) {
                    level2Button.getStyle().up = skin2.getDrawable("L2_hover.9");
                } else {
                    level2Button.getStyle().up = skin2.getDrawable("L2_up.9");
                }
            }
//
            if (!level3Button.isDisabled()) {
                if (level3Button.getStyle().up == skin3.getDrawable("L3_up.9")) {
                    level3Button.getStyle().up = skin3.getDrawable("L3_hover.9");
                } else {
                    level3Button.getStyle().up = skin3.getDrawable("L3_up.9");
                }
            }
        }
        stage.act(delta);
        stage.draw();
    }

    public void completedLevel1() {
        System.out.println("COMPLETED LEVEL 1");
        level2Button.setDisabled(false);
        completedLevel1 = true;
        level1Button.getStyle().up = skin.getDrawable("L1_up");
    }

    public void completedLevel2() {
        System.out.println("COMPLETED LEVEL 2");
        level3Button.setDisabled(false);
        completedLevel2 = true;
        level1Button.getStyle().up = skin.getDrawable("L1_up");
        level2Button.getStyle().up = skin2.getDrawable("L2_up.9");
    }

    public void completedLevel3() {
        System.out.println("COMPLETED LEVEL 3");
        level1Button.getStyle().up = skin.getDrawable("L1_up");
        level2Button.getStyle().up = skin2.getDrawable("L2_up.9");
        level3Button.getStyle().up = skin3.getDrawable("L3_up.9");
    }

    public void disableLevel2(){
        completedLevel1 = false;
        level2Button.setDisabled(true);
    }
    public void disableLevel3(){
        completedLevel2 = false;
        level3Button.setDisabled(true);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
        skin.dispose();
    }
}
