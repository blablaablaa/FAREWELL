package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

public class DefeatScreen implements Screen {
    private COLE game;
    private SpriteBatch batch;
    private Texture victoryImage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Button continueButton;
    private Skin skin;
    private Music backgroundMusic;
    private Sound buttonClickSound;
    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 480;

    public DefeatScreen(COLE game, Screen screen) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        victoryImage = new Texture("defeat.png");
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("Button_sound.mp3"));

        skin = new Skin(Gdx.files.internal("ui/continue_skin.json"));
        continueButton = new Button(skin);

        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f, 2f, 0);
                backgroundMusic.stop();
                game.setScreen(screen);
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        table.add(continueButton).padBottom(20);
        stage.addActor(table);
    }

    @Override
    public void show() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Into_music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(victoryImage, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        victoryImage.dispose();
        stage.dispose();
    }
}
