package io.github.blablaablaa.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Level3Screen extends LevelScreen {

    public Level3Screen(COLE game) {
        super(game);
        background = new Texture("level3_background.jpg");
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
