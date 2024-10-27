package io.github.blablaablaa.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level1Screen extends LevelScreen {

    private Button pauseButton;
    private Table table;
    private boolean isPaused = false;
    private Texture blurredBackground;
    private Stage pauseStage;
    private SpriteBatch batch;
    private Catapult catapult;


    private RedBird redBird;
    private BlueBird blueBird;
    private BombBird bombBird;

    private SmallPig smallPig;
    private MediumPig mediumPig;
    private LargePig largePig;

    private WoodBlock woodBlock1, woodBlock2, woodBlock3, woodBlock4,
                    woodBlock5, woodBlock6, woodBlock7, woodBlock8;
    private GlassBlock glassBlock1, glassBlock2, glassBlock3;

    public Level1Screen(COLE game) {
        super(game);


        batch = new SpriteBatch();

        background = new Texture("level1_background.png");
        blurredBackground = new Texture("level1_background.png");

        pauseStage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        super.show();
        createLevel();
    }

    @Override
    protected void createLevel() {
        Skin pauseSkin = new Skin(Gdx.files.internal("ui/pause_skin.json"));

        pauseButton = new Button(pauseSkin);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = true;
            }
        });

        table = new Table();
        table.top().left();
        table.setFillParent(true);

//        table.setDebug(true);

        table.add(pauseButton).pad(10).size(70, 70);

        stage.addActor(table);
        createPauseMenu();

        redBird = new RedBird(140, 190);
//        float screenWidth = WORLD_WIDTH;
//        float screenHeight = WORLD_HEIGHT;
        blueBird = new BlueBird(100, 130);
        bombBird = new BombBird(55,135);

        smallPig = new SmallPig(560, 210);
        mediumPig = new MediumPig(625, 210);
        largePig = new LargePig(700, 130);

        woodBlock1 = new WoodBlock(550, 130, 0);
        woodBlock2 = new WoodBlock(580, 130, 0);
        woodBlock3 = new WoodBlock(610, 130, 0);
        woodBlock4 = new WoodBlock(580, 170, 90);

        woodBlock5 = new WoodBlock(620, 130, 0);
        woodBlock6 = new WoodBlock(645, 130, 0);
        woodBlock7 = new WoodBlock(675, 130, 0);
        woodBlock8 = new WoodBlock(645, 170, 90);

        glassBlock1 = new GlassBlock(615, 210, 0);

        glassBlock2 = new GlassBlock(675, 210, 0);
        glassBlock3 = new GlassBlock(645, 245, 90);
        catapult = new Catapult("catapult.png", 150, 135, 0.01f);
    }


    private void createPauseMenu() {
        Skin resumeSkin = new Skin(Gdx.files.internal("ui/resume_skin.json"));
        Skin exitSkin = new Skin(Gdx.files.internal("ui/exit_skin.json"));

        Button resumeButton = new Button(resumeSkin);
        Button exitButton = new Button(exitSkin);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectionScreen(game));
            }
        });

        Table pauseTable = new Table();
        pauseTable.center();
        pauseTable.setFillParent(true);

        pauseTable.add(resumeButton).size(170, 50).padBottom(20);
        pauseTable.row();
        pauseTable.add(exitButton).size(170, 50);

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
            batch.begin();
            batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);

            catapult.render(batch);

            redBird.render(batch);
            blueBird.render(batch);
            bombBird.render(batch);

            smallPig.render(batch);
            mediumPig.render(batch);
            largePig.render(batch);

            woodBlock1.render(batch);
            woodBlock2.render(batch);
            woodBlock3.render(batch);
            woodBlock4.render(batch);
            woodBlock5.render(batch);
            woodBlock6.render(batch);
            woodBlock7.render(batch);
            woodBlock8.render(batch);

            glassBlock1.render(batch);

            glassBlock2.render(batch);
            glassBlock3.render(batch);
            // Debugging: Check if catapult position is within bounds
//            System.out.println("Catapult Position: (" + catapult.xPosition + ", " + catapult.yPosition + ")");

            batch.end();

            stage.act(delta);
            stage.draw();

            Gdx.input.setInputProcessor(stage);
        }
    }


    @Override
    public void dispose() {
        super.dispose();
        blurredBackground.dispose();
        pauseStage.dispose();
        batch.dispose();
        redBird.dispose();
//        if (catapult != null) {
            catapult.dispose();
//        }
    }
}
