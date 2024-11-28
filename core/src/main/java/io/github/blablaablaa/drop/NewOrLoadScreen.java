package io.github.blablaablaa.drop;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class NewOrLoadScreen extends Game implements Screen{
    private COLE game;
    private Texture background;
    private Stage stage;
    private Skin loadSkin, newSkin;
    private Button loadButton, newButton;
    private Table table;
    private SpriteBatch batch;
    private ScreenViewport viewport;
    private static final String SAVE_FILE = "save_file.txt";
    private Sound buttonClickSound;
    public NewOrLoadScreen(COLE game) {
        this.game = game;
        this.viewport = new ScreenViewport();
        this.stage = new Stage(viewport);
        table = new Table();
        batch = new SpriteBatch();
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("Button_sound.mp3"));
    }

    public GameState loadGameState() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE));
            GameState gameState = (GameState) ois.readObject();
            ois.close();
            System.out.println("Game state loaded successfully!");
            return gameState;
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("No saved game state. Returning default state.");
            return new GameState(false, false);
        }
    }


    @Override
    public void show(){
        background = new Texture(Gdx.files.internal("agBIRDS.jpg"));
        loadSkin = new Skin(Gdx.files.internal("ui/load_skin.json"));
        newSkin = new Skin(Gdx.files.internal("ui/new_game_skin.json"));

        loadButton = new Button(loadSkin);
        newButton = new Button(newSkin);

        loadButton.setSize(viewport.getWorldWidth() * 0.3f + 50, viewport.getWorldHeight() * 0.1f);
        newButton.setSize(viewport.getWorldWidth() * 0.3f + 50, viewport.getWorldHeight() * 0.1f);

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f,2f,0);
                System.out.println("Loading...");
                GameState gameState = loadGameState();
                boolean completedLevel1 = gameState.completedLevel1;;
                boolean completedLevel2 = gameState.completedLevel2;;
                System.out.println(completedLevel1);
                System.out.println(completedLevel2);
                if(completedLevel1) COLE.levelSelectionScreen.completedLevel1();
                if(completedLevel2) COLE.levelSelectionScreen.completedLevel2();
                game.switchToLevelSelection();
            }
        });

        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.7f,2f,0);
                System.out.println("NEW GAME...");
                COLE.levelSelectionScreen.disableLevel2();
                COLE.levelSelectionScreen.disableLevel3();
                game.switchToLevelSelection();
            }
        });


        table.setFillParent(true);
        table.center();
        table.add(newButton).size(newButton.getWidth(), newButton.getHeight()).padBottom(10);
        table.row();
        table.add(loadButton).size(loadButton.getWidth(), loadButton.getHeight()).padBottom(10);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);

        loadButton.setSize(viewport.getWorldWidth() * 0.3f + 50, viewport.getWorldHeight() * 0.1f);
        newButton.setSize(viewport.getWorldWidth() * 0.3f + 50, viewport.getWorldHeight() * 0.1f);

        table.clear();
        table.center();
        table.add(newButton).size(newButton.getWidth(), newButton.getHeight()).padBottom(10);
        table.row();
        table.add(loadButton).size(loadButton.getWidth(), loadButton.getHeight()).padBottom(10);

        stage.addActor(table);
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

    }


}
