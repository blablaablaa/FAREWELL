package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class LevelSelectionScreen implements Screen {
    private COLE game;
    private SpriteBatch batch;
    private Texture background;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Skin skin, vicSkin, defSkin;
    private Table table;
    private float timeAccumulator = 0f;
    private static final float SWITCH_INTERVAL = 0.5f;
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 480;
    private Button level1Button, level2Button, level3Button, vicButton, defButton;

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
        vicSkin = new Skin(Gdx.files.internal("ui/vic_button.json"));
        defSkin = new Skin(Gdx.files.internal("ui/def_button.json"));


        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        bottomTable.bottom().padBottom(30);
        stage.addActor(bottomTable);

        createLevelButtons();
        createBottomButtons(bottomTable);
    }

    private void createLevelButtons() {
        level1Button = new Button(skin);
        level2Button = new Button(skin);
        level3Button = new Button(skin);
        level2Button.setDisabled(true);
        level3Button.setDisabled(true);

        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!level1Button.isDisabled()) {
                    game.setScreen(new Level1Screen(game));
                }
            }
        });

        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!level2Button.isDisabled()) {
                    game.setScreen(new Level2Screen(game));
                }
            }
        });

        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!level3Button.isDisabled()) {
                    game.setScreen(new Level3Screen(game));
                }
            }
        });

        table.add(level1Button).size(90, 90).padRight(50).center().pad(10);
        table.add(level2Button).size(90, 90).center().pad(150);
        table.add(level3Button).size(90, 90).padLeft(50).center().pad(10);
        table.padTop(50);
    }

    private void createBottomButtons(Table bottomTable) {
        vicButton = new Button(vicSkin);
        defButton = new Button(defSkin);

        vicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new VictoryScreen(game));
            }
        });

        defButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new DefeatScreen(game));
            }
        });


        bottomTable.add(vicButton).size(160, 60).padRight(20).padLeft(50);
        bottomTable.add().expandX();
        bottomTable.add(defButton).size(160, 60).padLeft(20).padRight(50);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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

//            if (!level2Button.isDisabled()) { ... }
//            if (!level3Button.isDisabled()) { ... }
        }

        stage.act(delta);
        stage.draw();
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
