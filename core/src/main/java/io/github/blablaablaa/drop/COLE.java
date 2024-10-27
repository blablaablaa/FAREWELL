package io.github.blablaablaa.drop;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

public class COLE extends Game {
    private Screen tapToPlayScreen;
    private Screen levelSelectionScreen;
    private Screen victoryScreen;
    private Screen defeatScreen;

    @Override
    public void create() {
        tapToPlayScreen = new TapToPlayScreen(this);
        levelSelectionScreen = new LevelSelectionScreen(this);
        victoryScreen = new VictoryScreen(this);
        defeatScreen = new DefeatScreen(this);

        setScreen(tapToPlayScreen);
//        setScreen(victoryScreen);
//        setScreen(defeatScreen);
    }

    public void switchToLevelSelection() {
        setScreen(levelSelectionScreen);
    }
}


