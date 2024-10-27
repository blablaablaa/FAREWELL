package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Level2Screen extends LevelScreen {

    public Level2Screen(COLE game) {
        super(game);
        background = new Texture("level2_background.jpg");
    }

    @Override
    protected void createLevel() {
//        Button backButton = new Button(skin);
//        backButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new LevelSelectionScreen(game));
//            }
//        });
//
//        stage.addActor(backButton);
    }
}
