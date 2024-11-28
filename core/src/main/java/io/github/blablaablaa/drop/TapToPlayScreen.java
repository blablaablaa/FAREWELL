package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.audio.Music;

public class TapToPlayScreen implements Screen {
    private COLE game;
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private ScreenViewport viewport;
    private Stage stage;
    private Skin skin, skin2;
    private Button titleButton, tapToPlayButton;
    private Table table;
    private float timeAccumulator = 0f;
    private float switchInterval = 0.5f;

    public static Music backgroundMusic;
    private Sound buttonClickSound;

    public TapToPlayScreen(COLE game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.viewport = new ScreenViewport();
        this.stage = new Stage(viewport);
        this.table = new Table();
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("Button_sound.mp3"));
//        buttonClickSound.setPitch(1,1,0);
    }

    @Override
    public void show() {
        backgroundTexture = new Texture(Gdx.files.internal("agBIRDS.jpg"));
        skin = new Skin(Gdx.files.internal("ui/title_skin.json"));
        skin2 = new Skin(Gdx.files.internal("ui/tapToPlay_skin.json"));

        titleButton = new Button(skin);
        tapToPlayButton = new Button(skin2);

        titleButton.setSize(viewport.getWorldWidth() * 0.3f + 100, viewport.getWorldHeight() * 0.1f);
        tapToPlayButton.setSize(viewport.getWorldWidth() * 0.2f, viewport.getWorldHeight() * 0.04f);

        table.setFillParent(true);
        table.center();
        table.add(titleButton).size(titleButton.getWidth(), titleButton.getHeight()).padBottom(50);
        table.row();
        table.add(tapToPlayButton).size(tapToPlayButton.getWidth(), tapToPlayButton.getHeight());

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Into_music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        timeAccumulator += delta;
        if (timeAccumulator >= switchInterval) {
            timeAccumulator = 0f;
            if (tapToPlayButton.getStyle().up == skin2.getDrawable("Tap to play")) {
                tapToPlayButton.getStyle().up = skin2.getDrawable("Tap to play (1)");
            } else {
                tapToPlayButton.getStyle().up = skin2.getDrawable("Tap to play");
            }
        }

        stage.act(delta);
        stage.draw();

        if (Gdx.input.justTouched()) {
            buttonClickSound.play(0.7f,2f,0);
            System.out.println("Touch me daddy!");
//            backgroundMusic.stop();
            game.switchToLoadScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);

        titleButton.setSize(viewport.getWorldWidth() * 0.3f + 100, viewport.getWorldHeight() * 0.1f);
        tapToPlayButton.setSize(viewport.getWorldWidth() * 0.2f, viewport.getWorldHeight() * 0.04f);

        table.clear();
        table.add(titleButton).size(titleButton.getWidth(), titleButton.getHeight()).padBottom(50);
        table.row();
        table.add(tapToPlayButton).size(tapToPlayButton.getWidth(), tapToPlayButton.getHeight());
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        skin.dispose();
        stage.dispose();
        batch.dispose();
        backgroundMusic.stop();
        backgroundMusic.dispose();
    }
}
